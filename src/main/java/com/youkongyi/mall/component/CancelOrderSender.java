package com.youkongyi.mall.component;

import com.youkongyi.mall.dto.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

/**
  * @description： 取消订单消息的发出者
  *     com.youkongyi.mall.component.CancelOrderSender.
  * @author： Aimer
  * @crateDate： 2022/7/25 17:36
  */
@Slf4j
@Component
public class CancelOrderSender {

    private final AmqpTemplate amqpTemplate;

    public CancelOrderSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    /**
      * @description： 发送消息并设置过期时间
      *     com.youkongyi.mall.component.CancelOrderSender.sendMessage
      * @param： orderId (java.lang.Long)
     * @param： delayTimes (long)
      * @return： void
      * @author： Aimer
      * @crateDate： 2022/7/25 18:14
      */
    public void sendMessage(Long orderId,final long delayTimes){
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, message -> {
            //给消息设置延迟毫秒值
            message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
            return message;
        });
        log.info("send delay message orderId:{}",orderId);
    }
}
