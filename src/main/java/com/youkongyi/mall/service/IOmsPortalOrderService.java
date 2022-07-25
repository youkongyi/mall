package com.youkongyi.mall.service;

import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

/**
  * @description： 前台订单管理Service
  *     com.youkongyi.mall.service.IOmsPortalOrderService.
  * @author： Aimer
  * @crateDate： 2022/7/25 17:40
  */
public interface IOmsPortalOrderService {

    /**
      * @description： 根据提交信息生成订单
      *     com.youkongyi.mall.service.IOmsPortalOrderService.generateOrder
      * @param： orderParam (com.youkongyi.mall.dto.OrderParam) 
      * @return： com.youkongyi.mall.common.util.ReturnDTO          
      * @author： Aimer
      * @crateDate： 2022/7/25 17:58
      */
    @Transactional
    ReturnDTO<String> generateOrder(OrderParam orderParam);

    /**
      * @description： 取消单个超时订单
      *     com.youkongyi.mall.service.IOmsPortalOrderService.cancelOrder
      * @param： orderId (java.lang.Long) 
      * @return： void          
      * @author： Aimer
      * @crateDate： 2022/7/25 17:58
      */
    @Transactional
    void cancelOrder(Long orderId);
}
