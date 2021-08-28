package com.hyf.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://graph.qq.com/oauth2.0/show
 * ?which=Login
 * &display=pc
 * &client_id=100312028
 * &response_type=code
 * &redirect_uri=https%3A%2F%2Fpassport.baidu.com%2Fphoenix%2Faccount%2Fafterauth%3Fmkey%3D935349d357a08623b78adb3e0621e567f82ef06046f60de458%26tpl%3Dnetdisk
 * &state=1616663151
 * &display=page
 * &scope=get_user_info%2Cadd_share%2Cget_other_info%2Cget_fanslist%2Cget_idollist%2Cadd_idol%2Cget_simple_userinfo
 * &traceid=
 */
@SpringBootApplication
public class Oauth2ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ClientApplication.class, args);
    }

}
