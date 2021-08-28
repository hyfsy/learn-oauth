package com.hyf.oauth2.util;

import com.hyf.oauth2.model.AppInfo;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author baB_hyf
 * @date 2021/03/19
 */
public class CodeGenerator {

    private static final ConcurrentMap<String, AppInfo> codeCacheMap = new ConcurrentHashMap<>();

    public static String generate(AppInfo appInfo, String scope) {
        // code生成算法
        String code = generateScopeIdentify(appInfo, scope);

        codeCacheMap.put(code, appInfo);

        return code;
    }

    private static String generateScopeIdentify(AppInfo appInfo, String scope) {
        // scope生成算法
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean valid(String code) {
        AppInfo appInfo = codeCacheMap.remove(code);
        return appInfo != null;
    }

}
