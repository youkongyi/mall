package com.youkongyi.mall.component;

import com.youkongyi.mall.service.IOmsPortalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
  * @description： 取消订单消息的处理者
  *     com.youkongyi.mall.component.CancelOrderReceiver
  * @author： Aimer
  * @crateDate： 2022/7/25 17:39
  */
@Slf4j
@Component
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {

    private final IOmsPortalOrderService portalOrderService;

    @Autowired
    public CancelOrderReceiver(IOmsPortalOrderService portalOrderService) {
        this.portalOrderService = portalOrderService;
    }

    @RabbitHandler
    public void handle(Long orderId){
        log.info("receive delay message orderId:{}",orderId);
        portalOrderService.cancelOrder(orderId);
    }

}
