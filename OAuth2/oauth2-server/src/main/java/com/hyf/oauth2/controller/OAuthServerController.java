package com.hyf.oauth2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyf.oauth2.model.AppInfo;
import com.hyf.oauth2.model.TokenInfo;
import com.hyf.oauth2.util.AppInfoRegister;
import com.hyf.oauth2.util.CodeGenerator;
import com.hyf.oauth2.util.TokenGuard;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author baB_hyf
 * @date 2021/03/19
 */
@RestController
@RequestMapping("oauth")
public class OAuthServerController {

    @CrossOrigin
    @GetMapping("authorize")
    public void authorize(@RequestParam("response_type") String responseType,
                          @RequestParam("client_id") String clientId,
                          @RequestParam("redirect_uri") String redirectUri,
                          @RequestParam(value = "scope", required = false) String scope,
                          HttpServletResponse response) {

        AppInfo appInfo = AppInfoRegister.getAppInfo(clientId);
        if (appInfo == null) {
            System.out.println("appInfo为空！");
            return;
        }

        switch (responseType) {
            case "code":
                String code = CodeGenerator.generate(appInfo, scope);
                String redirectUrl = redirectUri + "&code=" + code;
                redirect(response, redirectUrl);
                break;
            case "token":
                // TODO
                break;
            default:
                System.out.println("无效的responseType");
                break;
        }
    }

    @PostMapping("token")
    public TokenInfo token(@RequestBody Map<String, Object> params,
                        HttpServletResponse resp) throws IOException {

        String clientId = (String)params.get("client_id");
        String clientSecret = (String)params.get("client_secret");
        String grantType = (String)params.get("grant_type");
        String code = (String)params.get("code");
        String redirectUri = (String)params.get("redirect_uri");
        code = URLDecoder.decode(code, "UTF-8");

        switch (grantType) {
            case "authorization_code":
                if (CodeGenerator.valid(code)) {
                    AppInfo appInfo = AppInfoRegister.getAppInfo(clientId);
                    if (appInfo.getClientSecret().equals(clientSecret)) {
                        TokenInfo tokenInfo = TokenGuard.createToken(code);

                        return tokenInfo;

                        // TODO
                        // ObjectMapper objectMapper = new ObjectMapper();
                        // String token = objectMapper.writeValueAsString(tokenInfo);
                        // return token;
                        // resp.sendRedirect(redirectUri + "&tokenInfo=" + token);
                    }
                }
                break;
            case "password":
                // TODO
                break;
            case "client_credentials":
                // TODO
                break;
        }
        return null;
    }

    public void redirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
