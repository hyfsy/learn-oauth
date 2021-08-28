package com.hyf.oauth2.controller;

import com.hyf.oauth2.model.TokenInfo;
import com.hyf.oauth2.util.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author baB_hyf
 * @date 2021/03/19
 */
@RestController
@RequestMapping("oauth")
public class OAuthClientController {

    @Autowired
    private Environment environment;

    @CrossOrigin
    @GetMapping("authorize")
    public void authorization(@RequestParam(value = "code", required = false) String code,
                          @RequestParam(value = "redirect_url", required = false) String redirectUrl,
                          HttpServletRequest req,
                          HttpServletResponse resp) throws IOException {

        if (code == null) {
            resp.sendError(403, "认证失败");
            return;
        }

        code = URLEncoder.encode(code, "UTF-8");

        // TODO
        String clientRedirectUrl = req.getRequestURL().toString().split(req.getContextPath())[0] + req.getContextPath();
        clientRedirectUrl = clientRedirectUrl.endsWith("/") ? clientRedirectUrl : clientRedirectUrl + "/";
        clientRedirectUrl = URLEncoder.encode(clientRedirectUrl + "oauth/token?redirect_url=" + redirectUrl, "UTF-8");

        String serverAuthenticationUrl = environment.getProperty("oauth.server-url");
        serverAuthenticationUrl = serverAuthenticationUrl.endsWith("/") ? serverAuthenticationUrl : serverAuthenticationUrl + "/";

        String grantType = "authorization_code";
        String clientId = environment.getProperty("oauth.client-id");
        String clientSecret = environment.getProperty("oauth.client-secret");

        StringBuilder sb = new StringBuilder(serverAuthenticationUrl);
        String tokenUrl = sb
                .append("oauth/token")
                // .append("?client_id=").append(clientId)
                // .append("&client_secret=").append(clientSecret)
                // .append("&grant_type=").append(grantType)
                // .append("&code=").append(code)
                // .append("&redirect_uri=").append(clientRedirectUrl)
                .toString();


        Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", grantType);
        params.put("code", code);
        params.put("redirect_uri", clientRedirectUrl); // TODO 多余


        RestTemplate restTemplate = new RestTemplate();
        TokenInfo tokenInfo = restTemplate.postForObject(tokenUrl, params, TokenInfo.class);
        if (tokenInfo != null && tokenInfo.getAccessToken() != null) {
            TokenCache.put(tokenInfo.getAccessToken(), tokenInfo);

            resp.sendRedirect(redirectUrl + "?access_token=" + tokenInfo.getAccessToken());
        }
    }

}
