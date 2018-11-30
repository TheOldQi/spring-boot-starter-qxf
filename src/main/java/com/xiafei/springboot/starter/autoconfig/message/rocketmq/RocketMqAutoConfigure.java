package com.xiafei.springboot.starter.autoconfig.message.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <P>Description: RocketMq自动配置. </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/10/17</P>
 * <P>UPDATE DATE: 2018/10/17</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
@Configuration
@EnableConfigurationProperties(value = RocketMqProperties.class)
public class RocketMqAutoConfigure {

    private static final Logger log = LoggerFactory.getLogger(RocketMqAutoConfigure.class);

    @Resource
    private RocketMqProperties rocketMqProperties;
    @Resource
    private ApplicationContext applicationContext;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rocket.mq.producer", name = "enable", havingValue = "true")
    public DefaultMQProducer defaultMQProducer() {
        log.info("mq生产者初始化，使用={}", rocketMqProperties.getProducer());
        final DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr(rocketMqProperties.getProducer().getNamesrv());
        defaultMQProducer.setProducerGroup(rocketMqProperties.getProducer().getGroup());
        defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return defaultMQProducer;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rocket.mq.consumer", name = "enable", havingValue = "true")
    public DefaultMQPushConsumer mqPushConsumer() {
        log.info("mq消费者分发器初始化，使用={}", rocketMqProperties.getConsumer());
        final Map<String, RocketMqListener> beansOfType = applicationContext.getBeansOfType(RocketMqListener.class);
        if (beansOfType == null || beansOfType.isEmpty()) {
            log.info("项目中没有mq消费者，返回");
            return null;
        }
        final Map<String, String> subscription = new HashMap<>();

        for (Map.Entry<String, RocketMqListener> entry : beansOfType.entrySet()) {
            final String tag = entry.getValue().getTag();
            final String topic = entry.getValue().getTopic();
            log.info("mq消费者发现，topic={},tag={}，类={}", topic, tag, entry.getValue().getClass().getName());

            if (tag == null || tag.trim().equals("") || topic == null || topic.trim().equals("")) {
                log.error("该模式下topic和tag不允许为空");
                throw new RuntimeException("mq消费者的tag不允许为空");
            }
            String tags = subscription.get(topic);
            if (tags == null) {
                subscription.put(topic, tag);
            } else {
                subscription.put(topic, tags.concat("||").concat(tag));
            }
        }

        final DefaultMQPushConsumer defaultMQPushConsumer = defaultInstance();
        defaultMQPushConsumer.setSubscription(subscription);
        defaultMQPushConsumer.registerMessageListener(rocketMqConsumerDispatcher());
        return defaultMQPushConsumer;

    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rocket.mq.producer", name = "enable", havingValue = "true")
    public RocketMqPusher rocketMqPusher() {
        log.info("mq发送器初始化");
        return new RocketMqPusher();
    }

    @Bean(initMethod = "init")
    @ConditionalOnProperty(prefix = "rocket.mq.consumer", name = "enable", havingValue = "true")
    public RocketMqConsumerDispatcher rocketMqConsumerDispatcher() {
        return new RocketMqConsumerDispatcher();
    }


    private DefaultMQPushConsumer defaultInstance() {
        final DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(rocketMqProperties.getConsumer().getGroup());
        defaultMQPushConsumer.setNamesrvAddr(rocketMqProperties.getConsumer().getNamesrv());
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1024);
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return defaultMQPushConsumer;
    }

}
