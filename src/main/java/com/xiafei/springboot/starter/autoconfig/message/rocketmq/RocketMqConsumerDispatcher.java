package com.xiafei.springboot.starter.autoconfig.message.rocketmq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <P>Description: RocketMq分发器，替代默认的listener接口 </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/10/17</P>
 * <P>UPDATE DATE: 2018/10/17</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
public class RocketMqConsumerDispatcher implements MessageListenerConcurrently {

    private static final Logger log = LoggerFactory.getLogger(RocketMqConsumerDispatcher.class);

    @Resource
    private ApplicationContext applicationContext;

    private static Map<String, RocketMqListener> BEANS_OF_TAG = new HashMap<>();

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        final String logPrefix = "mq-consumer :  "; // 日志前缀

        for (MessageExt msg : list) {
            log.info("{} Topic={}, Tags={}, MsgId={}", logPrefix, msg.getTopic(), msg.getTags(), msg.getMsgId());

            final RocketMqListener rocketMqListener = BEANS_OF_TAG.get(msg.getTags());
            if (rocketMqListener == null) {
                log.error("tags无法匹配到消费者");
                continue;
            }
            try {
                rocketMqListener.consumeMessage(Collections.singletonList(msg));
            } catch (Exception e) {
                log.error("{} 发生异常：{}", logPrefix, e.getMessage(), e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public void init() {
        log.info("rokectMq分发器初始化");
        final Map<String, RocketMqListener> beansOfType = applicationContext.getBeansOfType(RocketMqListener.class);
        for (Map.Entry<String, RocketMqListener> entry : beansOfType.entrySet()) {
            if (entry.getValue().getTag() == null || entry.getValue().getTag().trim().equals("")) {
                log.error("tag不允许为空");
                throw new RuntimeException("tag不允许为空");
            }
            BEANS_OF_TAG.put(entry.getValue().getTag(), entry.getValue());
        }

    }


}
