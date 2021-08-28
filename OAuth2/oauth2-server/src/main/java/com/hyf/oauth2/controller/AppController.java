package com.hyf.oauth2.controller;

import com.hyf.oauth2.model.AppInfo;
import com.hyf.oauth2.util.AppInfoRegister;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author baB_hyf
 * @date 2021/03/19
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @PostMapping("/register")
    public String register(@RequestParam("appName") String appName,
                           @RequestParam("domain") String domain) {

        AppInfo appInfo = new AppInfo();
        appInfo.setAppName(appName);
        appInfo.setClientId(UUID.randomUUID().toString());
        appInfo.setClientSecret(UUID.randomUUID().toString());
        appInfo.setDomain(domain);
        AppInfoRegister.register(appInfo);

        return appInfo.getClientId();
    }

    /**
     * 不能显示获取，此处为了方便
     */
    @GetMapping("/info")
    public AppInfo info(@RequestParam("clientId") String clientId) {
        return AppInfoRegister.getAppInfo(clientId);
    }
}
