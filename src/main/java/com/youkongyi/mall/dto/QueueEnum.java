package com.youkongyi.mall.dto;

import lombok.Getter;

/**
 * @description： 消息队列枚举配置
 * com.youkongyi.mall.dto.QueueEnum
 * @author： Aimer
 * @crateDate： 2022/7/25 17:27
 */
@Getter
public enum QueueEnum {
    /**
     * 消息通知队列
     */
    QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),
    /**
     * 消息通知ttl队列(过期时间)
     */
    QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl");

    /**
     * 交换名称
     */
    private final String exchange;

    /**
     * 队列名称
     */
    private final String name;

    /**
     * 路由键
     */
    private final String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

}
