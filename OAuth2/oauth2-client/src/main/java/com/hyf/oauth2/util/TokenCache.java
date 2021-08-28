package com.hyf.oauth2.util;

import com.hyf.oauth2.model.TokenInfo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author baB_hyf
 * @date 2021/03/26
 */
public class TokenCache {

    private static final ConcurrentMap<String, TokenInfo> tokenCacheMap = new ConcurrentHashMap<>();

    public static void put(String key, TokenInfo tokenInfo) {
        tokenCacheMap.put(key, tokenInfo);
    }

    public static TokenInfo get(String key) {
        return tokenCacheMap.get(key);
    }
}
