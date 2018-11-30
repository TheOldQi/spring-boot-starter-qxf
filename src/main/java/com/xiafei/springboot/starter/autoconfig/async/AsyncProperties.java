package com.xiafei.springboot.starter.autoconfig.async;

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
@ConfigurationProperties("spring.async")
public class AsyncProperties extends HotPlus {

    /**
     * 异步线城数.
     */
    private Integer threads;

    /**
     * 发生异常时的描述文本.
     */
    private String errorLogText;

    public Integer getThreads() {
        return threads;
    }

    public void setThreads(final Integer threads) {
        this.threads = threads;
    }

    public String getErrorLogText() {
        return errorLogText;
    }

    public void setErrorLogText(final String errorLogText) {
        this.errorLogText = errorLogText;
    }
}
