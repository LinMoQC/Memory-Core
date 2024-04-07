package com.linmoblog.server.search.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.IndexesQuery;
import com.meilisearch.sdk.model.Results;
import com.meilisearch.sdk.model.SwapIndexesParams;
import com.meilisearch.sdk.model.TaskInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Poison02
 * @date 2024/4/7
 */
@Component
public class IndexService {

    @Resource
    private Client client;

    /**
     * 获取所有 index
     *
     * @return
     * @throws MeilisearchException
     */
    public Map<String, Object> all() throws MeilisearchException {
        return transformRes(client.getIndexes());
    }

    /**
     * 返回某些 index
     *
     * @param limit
     * @return
     * @throws MeilisearchException
     */
    public Map<String, Object> indexLimit(Integer limit) throws MeilisearchException {

        IndexesQuery query = new IndexesQuery().setLimit(limit);

        return transformRes(client.getIndexes(query));

    }

    /**
     * 获取某个 index
     *
     * @param indexName
     * @return
     * @throws MeilisearchException
     */
    public Map<String, Object> getOneIndex(String indexName) throws MeilisearchException {
        return transFormIndex(client.getIndex(indexName));
    }

    /**
     * 创建 index
     *
     * @param uid
     * @param primaryKey
     * @return
     * @throws MeilisearchException
     */
    public TaskInfo createIndex(String uid, String primaryKey) throws MeilisearchException {
        TaskInfo index = client.createIndex(uid, primaryKey);
        return index;
    }

    /**
     * 更新 index
     *
     * @param uid
     * @param primaryKey
     * @return
     * @throws MeilisearchException
     */
    public TaskInfo updateIndex(String uid, String primaryKey) throws MeilisearchException {
        TaskInfo index = client.updateIndex(uid, primaryKey);
        return index;
    }

    /**
     * 更新 index
     *
     * @param uid
     * @return
     * @throws MeilisearchException
     */
    public TaskInfo updateIndex(String uid) throws MeilisearchException {
        TaskInfo index = client.deleteIndex(uid);
        return index;
    }

    /**
     * 交换 index
     *
     * @return
     * @throws MeilisearchException
     */
    public TaskInfo swapIndex() throws MeilisearchException {
        SwapIndexesParams[] params =
                new SwapIndexesParams[]{
                        new SwapIndexesParams().setIndexes(new String[]{"indexA", "indexB"}),
                        new SwapIndexesParams().setIndexes(new String[]{"indexX", "indexY"})
                };
        return client.swapIndexes(params);
    }

    private Map<String, Object> transformRes(Results<Index> results) {

        Map<String, Object> resMap = Maps.newLinkedHashMap();
        int limit = results.getLimit();
        int offset = results.getOffset();
        int total = results.getTotal();
        Index[] indices
                = results.getResults();
        resMap.put("limit", limit);
        resMap.put("offset", offset);
        resMap.put("total", total);
        List<Map<String, Object>> res = Lists.newArrayListWithCapacity(indices.length);

        for (Index index : indices) {
            res.add(transFormIndex(index));
        }
        resMap.put("results", res);
        return resMap;
    }

    private Map<String, Object> transFormIndex(Index index) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("uid", index.getUid());
        map.put("primaryKey", index.getPrimaryKey());
        map.put("createdAt", index.getCreatedAt());
        map.put("updatedAt", index.getUpdatedAt());
        return map;
    }

}
