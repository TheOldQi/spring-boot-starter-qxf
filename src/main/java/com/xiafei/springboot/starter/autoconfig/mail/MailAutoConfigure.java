package com.xiafei.springboot.starter.autoconfig.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <P>Description: 邮件发送自动配置. </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/10/26</P>
 * <P>UPDATE DATE: 2018/10/26</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
@Configuration
public class MailAutoConfigure {

    @Bean
    @ConditionalOnProperty(prefix = "spring.mail", name = "host")
    public SendMail sendMail() {
        return new SendMail();
    }
}
