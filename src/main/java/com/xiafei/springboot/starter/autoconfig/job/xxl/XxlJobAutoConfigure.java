package com.xiafei.springboot.starter.autoconfig.job.xxl;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */
@Configuration
@EnableConfigurationProperties(value = XxlJobProperties.class)
public class XxlJobAutoConfigure {

    private static final Logger log = LoggerFactory.getLogger(XxlJobAutoConfigure.class);

    @Resource
    private XxlJobProperties xxlJobProperties;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "xxl.job", name = "enable", havingValue = "true")
    public XxlJobExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        final XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
        xxlJobExecutor.setAdminAddresses(xxlJobProperties.getAdminAddresses());
        xxlJobExecutor.setAppName(xxlJobProperties.getExecutor().getAppname());
        xxlJobExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        xxlJobExecutor.setPort(xxlJobProperties.getExecutor().getPort());
        xxlJobExecutor.setAccessToken(xxlJobProperties.getAccesstoken());
        xxlJobExecutor.setLogPath(xxlJobProperties.getExecutor().getLogPath());
        xxlJobExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogretentiondays());
        return xxlJobExecutor;
    }

}