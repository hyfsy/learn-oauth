package com.hyf.oauth2.util;

import com.hyf.oauth2.model.TokenInfo;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author baB_hyf
 * @date 2021/03/20
 */
public class TokenGuard {

    private static final ConcurrentMap<String, TokenInfo> tokenCacheMap = new ConcurrentHashMap<>();

    public static TokenInfo createToken(String code) {
        // token算法
        String token = UUID.randomUUID().toString();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAccessToken(token);
        tokenInfo.setTokenType(code + " get bearer");
        tokenInfo.setExpiresIn(60);
        tokenInfo.setRefreshToken(UUID.randomUUID().toString());
        tokenInfo.setScope(code + " get scope");
        tokenInfo.setUid("001");
        tokenInfo.setInfo("{\"userId\":001}");
        tokenCacheMap.put(code, tokenInfo);
        return tokenInfo;
    }

    // TODO 还要考虑 expires_in refresh_token等
    public static boolean validToken(String clientId, String token) {
        TokenInfo tokenInfo = tokenCacheMap.get(clientId);
        if (tokenInfo == null) {
            return false;
        }

        if (tokenInfo.getAccessToken().equals(token)) {
            return true;
        }
        return false;
    }

}
