package com.linmoblog.server.search.repository.impl;

import com.alibaba.fastjson2.JSON;
import com.linmoblog.server.search.repository.DocumentOperations;
import com.linmoblog.server.search.result.JsonHandler;
import com.linmoblog.server.search.result.SearchResult;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.*;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author Poison02
 * @date 2024/4/7
 */
public abstract class MeiliSearchRepository<T> implements DocumentOperations<T> {

    /**
     * 实体类代表的索引
     */
    @Setter
    private Index index;
    @Setter
    private Class<T> tClass;

    private JsonHandler jsonHandler = new JsonHandler();

    @Override
    public T get(String identifier) throws MeilisearchException {
        return index.getDocument(identifier, tClass);
    }

    @Override
    public Results<T> list() throws MeilisearchException {
        return list(null, tClass);
    }

    @Override
    public Results<T> list(int limit) throws MeilisearchException {
        DocumentsQuery query = new DocumentsQuery();
        query.setLimit(limit);

        return list(query, tClass);
    }

    @Override
    public Results<T> list(int offset, int limit) throws MeilisearchException {

        DocumentsQuery query = new DocumentsQuery();
        query.setLimit(limit);
        query.setOffset(offset);

        return list(query, tClass);
    }

    @Override
    public Results<T> list(DocumentsQuery query) throws MeilisearchException {
        return list(query, tClass);
    }

    @Override
    public Results<T> list(DocumentsQuery query, Class<T> entityClass) throws MeilisearchException {

        if (Objects.isNull(query) && Objects.isNull(entityClass))
            throw new MeilisearchException(" DocumentsQuery and entityClass cannot be empty at the same time ! ");

        return Optional.ofNullable(
                        Objects.isNull(query)
                                ? index.getDocuments(entityClass)
                                : index.getDocuments(query, entityClass))
                .orElse(new Results<>());
    }

    @Override
    public TaskInfo add(T document) throws MeilisearchException {
        return add(Collections.singletonList(document));
    }

    @Override
    public int update(T document) throws MeilisearchException {
        return update(Collections.singletonList(document));
    }

    @Override
    public TaskInfo add(List<T> documents) throws MeilisearchException {
        return index.addDocuments(JSON.toJSONString(documents));
    }

    @Override
    public int update(List<T> documents) throws MeilisearchException {
        return index.updateDocuments(JSON.toJSONString(documents)).getTaskUid();
    }

    @Override
    public int delete(String identifier) throws MeilisearchException {
        return index.deleteDocument(identifier).getTaskUid();
    }

    @Override
    public int deleteBatch(String... documentsIdentifiers) throws MeilisearchException {
        return index.deleteDocuments(Arrays.asList(documentsIdentifiers)).getTaskUid();
    }

    @Override
    public int deleteAll() throws MeilisearchException {
        return index.deleteAllDocuments().getTaskUid();
    }

    @Override
    public SearchResult<T> search(String q) throws MeilisearchException {
        String result = JSON.toJSONString(index.search(q));
        if (StringUtils.isEmpty(result)) return null;
        return jsonHandler.resultDecode(result, tClass);
    }

    @Override
    public SearchResult<T> search(String q, int offset, int limit) throws MeilisearchException {
        SearchRequest searchRequest = new SearchRequest(q);
        searchRequest.setOffset(offset);
        searchRequest.setLimit(limit);

        return search(searchRequest);
    }

    @Override
    public SearchResult<T> search(SearchRequest sr) throws MeilisearchException {
        String result = JSON.toJSONString(index.search(sr));

        return jsonHandler.resultDecode(result, tClass);
    }

    @Override
    public Settings getSettings() throws MeilisearchException {
        return index.getSettings();
    }

    @Override
    public TaskInfo updateSettings(Settings settings) throws MeilisearchException {
        return index.updateSettings(settings);
    }

    @Override
    public TaskInfo resetSettings() throws MeilisearchException {
        return index.resetSettings();
    }

    @Override
    public Task getUpdate(int updateId) throws MeilisearchException {
        return index.getTask(updateId);
    }
}

