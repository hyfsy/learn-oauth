package com.hyf.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 授权码（authorization-code）
 * 隐藏式（implicit）
 * 密码式（password）：
 * 客户端凭证（client credentials）
 */
@SpringBootApplication
public class Oauth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ServerApplication.class, args);
    }

}
