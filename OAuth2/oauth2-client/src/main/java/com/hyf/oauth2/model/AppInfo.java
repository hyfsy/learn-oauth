package com.hyf.oauth2.model;

/**
 * @author baB_hyf
 * @date 2021/03/19
 */
public class AppInfo {

    private String appName;
    private String clientId;
    private String clientSecret;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
