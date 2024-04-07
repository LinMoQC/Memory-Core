package com.linmoblog.server.search.result;

import com.meilisearch.sdk.json.GsonJsonHandler;

import java.util.List;

/**
 * @author Poison02
 * @date 2024/4/7
 */
public class JsonHandler {

    private com.meilisearch.sdk.json.JsonHandler jsonHandler = new GsonJsonHandler();

    public JsonHandler() {
    }

    public <T> SearchResult<T> resultDecode(String o, Class<T> clazz) {
        Object result = null;

        try {
            result = this.jsonHandler.decode(o, SearchResult.class, new Class[]{clazz});
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result == null ? null : (SearchResult)result;
    }

    public <T> List<T> listDecode(Object o, Class<T> clazz) {
        Object list = null;

        try {
            list = this.jsonHandler.decode(o, List.class, new Class[]{clazz});
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return list == null ? null : (List)list;
    }

    public String encode(Object o) {
        try {
            return this.jsonHandler.encode(o);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public <T> T decode(Object o, Class<T> clazz) {
        T t = null;

        try {
            t = this.jsonHandler.decode(o, clazz, new Class[0]);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return t;
    }

}
