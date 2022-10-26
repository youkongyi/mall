package com.youkongyi.mall.service.impl;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.component.CancelOrderSender;
import com.youkongyi.mall.dto.OrderParam;
import com.youkongyi.mall.service.IOmsPortalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
  * @description： 前台订单管理Service
  *     com.youkongyi.mall.service.impl.OmsPortalOrderServiceImpl
  * @author： Aimer
  * @crateDate： 2022/7/25 17:59
  */
@Slf4j
@Service
public class OmsPortalOrderServiceImpl implements IOmsPortalOrderService {
    
    private final CancelOrderSender cancelOrderSender;

    @Autowired
    public OmsPortalOrderServiceImpl(CancelOrderSender cancelOrderSender) {
        this.cancelOrderSender = cancelOrderSender;
    }

    /**
      * @description： 生成订单
      *     com.youkongyi.mall.service.impl.OmsPortalOrderServiceImpl.generateOrder
      * @param： orderParam (com.youkongyi.mall.dto.OrderParam)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.String>
      * @author： Aimer
      * @crateDate： 2022/7/25 18:02
      */
    @Override
    @Transactional()
    public ReturnDTO<String> generateOrder(OrderParam orderParam) {
        ReturnDTO<String> returnDTO = new ReturnDTO<>();
        log.info("process generateOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        this.sendDelayMessageCancelOrder(11L);
        returnDTO.setCode(ResultCode.SUCCESS.getCode());
        returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
        returnDTO.setData("下单成功");
        return returnDTO;
    }

    /**
      * @description： 取消订单操作
      *     com.youkongyi.mall.service.impl.OmsPortalOrderServiceImpl.cancelOrder
      * @param： orderId (java.lang.Long)
      * @return： void
      * @author： Aimer
      * @crateDate： 2022/7/25 18:02
      */
    @Override
    public void cancelOrder(Long orderId) {
        log.info("process cancelOrder orderId:{}",orderId);
    }

    /**
      * @description： 发送延迟消息
      *     com.youkongyi.mall.service.impl.OmsPortalOrderServiceImpl.sendDelayMessageCancelOrder
      * @param： orderId (java.lang.Long)
      * @return： void
      * @author： Aimer
      * @crateDate： 2022/7/25 18:02
      */
    private void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间，假设为60分钟
        long delayTimes = 30 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }
}
