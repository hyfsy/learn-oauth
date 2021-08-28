package com.hyf.oauth2;


import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author baB_hyf
 * @date 2021/03/20
 */
@Component
public class AuthenticationFilter implements Filter, EnvironmentAware {

    private Environment environment;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        // 写死
        boolean needAuth = checkNeedAuth(req);
        boolean isValidToken = validToken(req);
        if (!needAuth || isValidToken) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 拦截

        String serverAuthenticationUrl = environment.getProperty("oauth.server-url");
        if (serverAuthenticationUrl == null) {
            throw new IllegalArgumentException("oauth.server-url must not be null");
        }
        serverAuthenticationUrl = serverAuthenticationUrl.endsWith("/") ? serverAuthenticationUrl : serverAuthenticationUrl + "/";
        StringBuilder sb = new StringBuilder(serverAuthenticationUrl);

        // TODO 此处写死
        String responseType = "code";
        String clientId = environment.getProperty("oauth.client-id");
        String clientRedirectUrl = req.getRequestURL().toString().split(req.getContextPath())[0] + req.getContextPath();
        clientRedirectUrl = clientRedirectUrl.endsWith("/") ? clientRedirectUrl : clientRedirectUrl + "/";
        clientRedirectUrl = URLEncoder.encode(clientRedirectUrl + "oauth/authorize?redirect_url=" + URLEncoder.encode(req.getRequestURL().toString(), "UTF-8"), "UTF-8");

        String scope = req.getParameter("scope");
        String redirectUrl = sb
                .append("oauth/authorize")
                .append("?response_type=").append(responseType)
                .append("&client_id=").append(clientId)
                .append("&redirect_uri=").append(clientRedirectUrl)
                .append("&scope=").append(scope)
                .toString();
        resp.sendRedirect(redirectUrl);
    }

    public boolean validToken(HttpServletRequest req) {
        String token = null;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        if (token == null) {
            token = req.getParameter("access_token");
        }

        if (token == null) {
            return false;
        }

        // TODO 校验token

        return true;
    }

    public boolean checkNeedAuth(HttpServletRequest req) {
        String uri = req.getRequestURI();
        return uri.contains("/visit") && !uri.contains(".html") && !uri.contains("login");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
