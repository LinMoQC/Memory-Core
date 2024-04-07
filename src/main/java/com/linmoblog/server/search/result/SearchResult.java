package com.linmoblog.server.search.result;

import lombok.Data;

import java.util.List;

/**
 * @author Poison02
 * @date 2024/4/7
 */
@Data
public class SearchResult<T> {

    private String query;

    private long offset;

    private long limit;

    private long processingTimeMs;

    private long nbHits;

    private boolean exhaustiveNbHits;

    private List<T> hits;

    public SearchResult() {
    }

    public String toString() {
        return "SearchResult{query='" + this.query + '\'' + ", offset=" + this.offset + ", limit=" + this.limit + ", processingTimeMs=" + this.processingTimeMs + ", nbHits=" + this.nbHits + ", exhaustiveNbHits=" + this.exhaustiveNbHits + ", hits=" + this.hits + '}';
    }

}
