package com.xiafei.springboot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <P>Description: 自动配置允许跨域请求. </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/5/8</P>
 * <P>UPDATE DATE: 2018/5/8</P>
 *
 * @author qixiafei
 * @version 1.0
 * @since java 1.8.0
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CorsAutoConfiguration implements WebMvcConfigurer {

    /**
     * Configure cross origin requests processing.
     *
     * @since 4.2
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").
                allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS").
                allowCredentials(false).maxAge(3600);
    }
}
