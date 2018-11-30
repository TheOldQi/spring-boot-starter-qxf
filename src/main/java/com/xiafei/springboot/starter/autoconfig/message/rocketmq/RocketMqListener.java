package com.xiafei.springboot.starter.autoconfig.message.rocketmq;

import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * <P>Description: 标识mq监听器类型. </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2018/10/17</P>
 * <P>UPDATE DATE: 2018/10/17</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
public interface RocketMqListener {

    /**
     * mq消费方法.
     *
     * @param msgs 消息列表
     */
    void consumeMessage(List<MessageExt> msgs);

    /**
     * 返回该消费者消费的tag.
     *
     * @return 消费的tag
     */
    String getTag();

    /**
     * 返回该消费者消费的topic.
     *
     * @return 消费的tag
     */
    String getTopic();
}
