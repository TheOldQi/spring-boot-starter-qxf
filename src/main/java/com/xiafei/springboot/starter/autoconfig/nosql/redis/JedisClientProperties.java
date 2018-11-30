package com.xiafei.springboot.starter.autoconfig.nosql.redis;

import com.xiafei.springboot.starter.autoconfig.HotPlus;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <P>Description: . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2017/12/1</P>
 * <P>UPDATE DATE: 2017/12/1</P>
 *
 * @author qixiafei
 * @version 1.0
 * @since java 1.7.0
 */
@ConfigurationProperties(prefix = "jedis.client")
public class JedisClientProperties extends HotPlus {

    /**
     * redis服务器地址[host:port]，集群多个地址之间使用逗号分隔.
     */
    private String address;

    /**
     * redis服务密码.
     */
    private String password;

    /**
     * 最大线程数量.
     */
    private Integer maxTotal;

    /**
     * 最大空闲线程数量.
     */
    private Integer maxIdle;

    /**
     * 最大等待返回时间（毫秒）.
     */
    private Integer maxWaitTimeMs;

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(final Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(final Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMaxWaitTimeMs() {
        return maxWaitTimeMs;
    }

    public void setMaxWaitTimeMs(final Integer maxWaitTimeMs) {
        this.maxWaitTimeMs = maxWaitTimeMs;
    }

}
