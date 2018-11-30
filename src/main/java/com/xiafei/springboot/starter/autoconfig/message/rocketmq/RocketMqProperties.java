package com.xiafei.springboot.starter.autoconfig.message.rocketmq;

import com.xiafei.springboot.starter.autoconfig.HotPlus;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <P>Description: . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/10/17</P>
 * <P>UPDATE DATE: 2018/10/17</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
@ConfigurationProperties(prefix = "rocket.mq")
public class RocketMqProperties {


    private Consumer consumer;

    private Producer producer;

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(final Consumer consumer) {
        this.consumer = consumer;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(final Producer producer) {
        this.producer = producer;
    }

    public static class Consumer extends HotPlus {

        /**
         * 订阅的命名服务地址.
         */
        private String namesrv;

        /**
         * 一个项目保证一个就好.
         */
        private String group;

        public String getNamesrv() {
            return namesrv;
        }

        public void setNamesrv(final String namesrv) {
            this.namesrv = namesrv;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(final String group) {
            this.group = group;
        }
    }

    public static class Producer extends HotPlus {


        /**
         * 发布的命名服务地址.
         */
        private String namesrv;

        /**
         * 生产者组.
         */
        private String group;

        public String getNamesrv() {
            return namesrv;
        }

        public void setNamesrv(final String namesrv) {
            this.namesrv = namesrv;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(final String group) {
            this.group = group;
        }
    }
}
