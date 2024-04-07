package com.linmoblog.server.search.initializer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.linmoblog.server.search.annotation.MsField;
import com.linmoblog.server.search.annotation.MsIndex;
import com.linmoblog.server.search.repository.impl.MeiliSearchRepository;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.model.Results;
import com.meilisearch.sdk.model.Settings;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Poison02
 * @date 2024/4/7
 */
@Component
@Slf4j
public class MeiliSearchInitializer implements InitializingBean {

    @Resource
    private Client client;

    private Map<Class, MeiliSearchRepository> meiliSearchMapperMap = Maps.newConcurrentMap();

    public MeiliSearchInitializer(ObjectProvider<MeiliSearchRepository> objectProvider) {

        List<MeiliSearchRepository> collect = objectProvider.stream().collect(Collectors.toList());

        for (MeiliSearchRepository repository : collect) {
            log.info(repository.getClass().getName());
            /*Type actualTypeArgument = ((ParameterizedType) repository.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            meiliSearchMapperMap.put((Class<?>)actualTypeArgument, repository);*/
            // 用 AopProxyUtils.ultimateTargetClass(Object) 方法来获取原始类 操作原始类对象，不然这里获取不到泛型信息
            Class<?> targetClass = AopProxyUtils.ultimateTargetClass(repository);
            Type genericSuperclass = targetClass.getGenericSuperclass();

            // 检查 genericSuperclass 是否是 ParameterizedType 的实例
            if (genericSuperclass instanceof ParameterizedType) {
                Type actualTypeArgument = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
                meiliSearchMapperMap.put((Class<?>) actualTypeArgument, repository);
            } else {
                // 这里可以处理不是 ParameterizedType 的情况，比如记录错误或跳过
                log.error("The repository {} does not have a generic superclass", repository.getClass().getName());
            }
        }
        meiliSearchMapperMap.forEach((k, v) -> log.info("k:{} # v:{}", k, v));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initIndex();
    }

    /**
     * 初始化索引信息
     *
     * @throws Exception
     */
    public void initIndex() throws Exception {
        if (MapUtils.isEmpty(meiliSearchMapperMap)) return;

        for (Map.Entry<Class, MeiliSearchRepository> entry : meiliSearchMapperMap.entrySet()) {

            Class<?> tClass = entry.getKey();
            MeiliSearchRepository meilisearchRepository = entry.getValue();

            if (Objects.isNull(tClass)) continue;

            MsIndex annoIndex = tClass.getAnnotation(MsIndex.class);

            String uid = StringUtils.defaultString(annoIndex.uid(), tClass.getSimpleName().toLowerCase());
            String primaryKey = StringUtils.defaultString(annoIndex.primaryKey(), "id");

            int maxTotalHit = Optional.ofNullable(annoIndex.maxTotalHits()).orElse(1000);
            int maxValuesPerFacet = Optional.ofNullable(annoIndex.maxValuesPerFacet()).orElse(100);

            List<String> filterKey = Lists.newArrayList();
            List<String> sortKey = Lists.newArrayList();
            List<String> noDisPlay = Lists.newArrayList();

            //获取类所有属性
            for (Field field : tClass.getDeclaredFields()) {
                //判断是否存在这个注解
                if (field.isAnnotationPresent(MsField.class)) {
                    MsField annotation = field.getAnnotation(MsField.class);
                    if (annotation.openFilter()) {
                        filterKey.add(annotation.key());
                    }

                    if (annotation.openSort()) {
                        sortKey.add(annotation.key());
                    }
                    if (annotation.noDisplayed()) {
                        noDisPlay.add(annotation.key());
                    }
                }
            }

            // 索引存在则更新,不存在就创建
            Results<Index> indexes = client.getIndexes();
            if (Stream.of(Optional.ofNullable(indexes.getResults())
                            .orElseThrow(() -> new RuntimeException("Failed to get Indexes.")))
                    .anyMatch(result -> uid.equals(result.getUid()))
            ) {
                client.updateIndex(uid, primaryKey);
            } else {
                client.createIndex(uid, primaryKey);
            }


            Index index = client.getIndex(uid);
            meilisearchRepository.setIndex(index);
            meilisearchRepository.setTClass(tClass);

            Settings settings = new Settings();
            settings.setDisplayedAttributes(noDisPlay.size() > 0 ? noDisPlay.toArray(new String[noDisPlay.size()]) : new String[]{"*"});
            settings.setFilterableAttributes(filterKey.toArray(new String[filterKey.size()]));
            settings.setSortableAttributes(sortKey.toArray(new String[sortKey.size()]));
            index.updateSettings(settings);

        }

    }

}
