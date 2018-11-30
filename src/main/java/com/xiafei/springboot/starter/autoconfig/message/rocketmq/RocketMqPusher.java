package com.xiafei.springboot.starter.autoconfig.message.rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <P>Description: mq消息发送工具，因为mq可能发送过程中发生异常，但是实际上已经发送出去了. </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/10/26</P>
 * <P>UPDATE DATE: 2018/10/26</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
public class RocketMqPusher {

    private static final Logger log = LoggerFactory.getLogger(RocketMqPusher.class);

    @Autowired(required = false)
    private DefaultMQProducer producer;

    /**
     * 推送Mq消息.
     *
     * @param msg
     * @param topic
     * @param tag
     * @return
     */
    public SendResult send(String msg, String topic, String tag) {
        try {
            SendResult send = producer.send(new Message(topic, tag, msg.getBytes("utf-8")));
            return send;
        } catch (Exception e) {
            log.error("MQ发送失败", e);
            return null;
        }

    }
}
