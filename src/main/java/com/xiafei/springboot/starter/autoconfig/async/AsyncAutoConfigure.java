package com.xiafei.springboot.starter.autoconfig.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <P>Description: spring开启异步方法自动配置线程池. </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/7/26</P>
 * <P>UPDATE DATE: 2018/7/26</P>
 *
 * @author qixiafei
 * @version 1.0
 * @since java 1.8.0
 */
@Configuration
@EnableAsync
@ConditionalOnMissingBean(value = AsyncConfigurer.class)
@ConditionalOnProperty(prefix = "spring.async", name = "enable", havingValue = "true")
@EnableConfigurationProperties(value = AsyncProperties.class)
public class AsyncAutoConfigure implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(AsyncAutoConfigure.class);

    @Resource
    private AsyncProperties asyncProperties;

    @Override
    public Executor getAsyncExecutor() {
        Integer threads = asyncProperties.getThreads();
        threads = threads == null ? 10 : threads;
        return Executors.newFixedThreadPool(threads);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        final String errorText = (asyncProperties.getErrorLogText() == null || "".equals(asyncProperties.getErrorLogText().trim()))
                ? "异步线程池执行任务异常"
                : asyncProperties.getErrorLogText();
        return (throwable, method, objects) -> log.error(errorText, throwable);
    }
}
