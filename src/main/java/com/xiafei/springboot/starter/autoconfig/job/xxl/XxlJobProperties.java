package com.xiafei.springboot.starter.autoconfig.job.xxl;

import com.xiafei.springboot.starter.autoconfig.HotPlus;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <P>Description: . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/10/16</P>
 * <P>UPDATE DATE: 2018/10/16</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
@ConfigurationProperties("xxl.job")
public class XxlJobProperties extends HotPlus {

    /**
     * 调度中心地址，多个用逗号分隔.
     */
    private String adminAddresses;

    /**
     * 执行器配置.
     */
    private Executor executor;

    /**
     * 令牌，通调度中心配置.
     */
    private String accesstoken;

    public String getAdminAddresses() {
        return adminAddresses;
    }

    public void setAdminAddresses(final String adminAddresses) {
        this.adminAddresses = adminAddresses;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(final Executor executor) {
        this.executor = executor;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(final String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public static class Executor {

        /**
         * 应用名称.
         */
        private String appname;

        /**
         * 执行器IP默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用.
         */
        private String ip;

        /**
         * 端口号，内部jetty服务器的，不要和项目冲突.
         */
        private Integer port;

        /**
         * 日志路径.
         */
        private String logPath;

        /**
         * 日志保留天数.
         */
        private Integer logretentiondays;

        public String getAppname() {
            return appname;
        }

        public void setAppname(final String appname) {
            this.appname = appname;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(final String ip) {
            this.ip = ip;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(final Integer port) {
            this.port = port;
        }

        public String getLogPath() {
            return logPath;
        }

        public void setLogPath(final String logPath) {
            this.logPath = logPath;
        }

        public Integer getLogretentiondays() {
            return logretentiondays;
        }

        public void setLogretentiondays(final Integer logretentiondays) {
            this.logretentiondays = logretentiondays;
        }


    }
}
