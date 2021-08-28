package com.hyf.oauth2.util;

import com.hyf.oauth2.model.AppInfo;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author baB_hyf
 * @date 2021/03/19
 */
public class AppInfoRegister {

    private static final ConcurrentMap<String, AppInfo> appInfoCacheMap = new ConcurrentHashMap<>();

    static {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppName("测试账号");
        appInfo.setClientId("11111clientid11111");
        appInfo.setClientSecret("22222clientsecret22222");
        appInfoCacheMap.put(appInfo.getClientId(), appInfo);
    }

    public static void register(AppInfo appInfo) {
        appInfoCacheMap.put(appInfo.getClientId(), appInfo);
    }

    public static void unRegister(String clientId) {
        appInfoCacheMap.remove(clientId);
    }

    public static AppInfo getAppInfo(String clientId) {
        return appInfoCacheMap.get(clientId);
    }
}
