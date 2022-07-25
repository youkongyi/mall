package com.youkongyi.mall.config;

import com.youkongyi.mall.dto.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
  * @description： 消息队列配置
  *     com.youkongyi.mall.config.RabbitMqConfig
  * @author： Aimer
  * @crateDate： 2022/7/25 17:30
  */
@Configuration
public class RabbitMqConfig {

    /**
      * @description： 订单消息实际消费队列所绑定的交换机
      *     com.youkongyi.mall.config.RabbitMqConfig.orderDirect
      * @return： org.springframework.amqp.core.DirectExchange
      * @author： Aimer
      * @crateDate： 2022/7/25 17:30
      */
    @Bean
    public DirectExchange orderDirect(){
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
      * @description： 订单延迟队列队列所绑定的交换机
      *     com.youkongyi.mall.config.RabbitMqConfig.orderTtlDirect
      * @return： org.springframework.amqp.core.DirectExchange
      * @author： Aimer
      * @crateDate： 2022/7/25 17:31
      */
    @Bean
    DirectExchange orderTtlDirect() {
        return ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
                .durable(true)
                .build();
    }

    /**
      * @description： 订单实际消费队列
      *     com.youkongyi.mall.config.RabbitMqConfig.orderQueue
      * @return： org.springframework.amqp.core.Queue
      * @author： Aimer
      * @crateDate： 2022/7/25 17:32
      */
    @Bean
    public Queue orderQueue() {
        return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getName());
    }


    /**
      * @description： 订单延迟队列（死信队列）
      *     com.youkongyi.mall.config.RabbitMqConfig.orderTtlQueue
      * @return： org.springframework.amqp.core.Queue
      * @author： Aimer
      * @crateDate： 2022/7/25 17:32
      */
    @Bean
    public Queue orderTtlQueue() {
        return QueueBuilder
                .durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_ORDER_CANCEL.getExchange())//到期后转发的交换机
                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey())//到期后转发的路由键
                .build();
    }

    /**
      * @description： 将订单队列绑定到交换机
      *     com.youkongyi.mall.config.RabbitMqConfig.orderBinding
      * @param： orderDirect (org.springframework.amqp.core.DirectExchange)
     * @param： orderQueue (org.springframework.amqp.core.Queue)
      * @return： org.springframework.amqp.core.Binding
      * @author： Aimer
      * @crateDate： 2022/7/25 17:33
      */
    @Bean
    Binding orderBinding(DirectExchange orderDirect, Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.QUEUE_ORDER_CANCEL.getRouteKey());
    }

    /**
      * @description： 将订单延迟队列绑定到交换机
      *     com.youkongyi.mall.config.RabbitMqConfig.orderTtlBinding
      * @param： orderTtlDirect (org.springframework.amqp.core.DirectExchange)
     * @param： orderTtlQueue (org.springframework.amqp.core.Queue)
      * @return： org.springframework.amqp.core.Binding
      * @author： Aimer
      * @crateDate： 2022/7/25 17:33
      */
    @Bean
    Binding orderTtlBinding(DirectExchange orderTtlDirect,Queue orderTtlQueue){
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirect)
                .with(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey());
    }
}
