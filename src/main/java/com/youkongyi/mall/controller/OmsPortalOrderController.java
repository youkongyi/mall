package com.youkongyi.mall.controller;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.dto.OrderParam;
import com.youkongyi.mall.service.IOmsPortalOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description： 订单管理Controller
 * com.youkongyi.mall.controller.OmsPortalOrderController.
 * @author： Aimer
 * @crateDate： 2022/7/25 18:07
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Tag(name = "订单管理", description = "订单管理")
public class OmsPortalOrderController {

    private final IOmsPortalOrderService portalOrderService;

    @Autowired
    public OmsPortalOrderController(IOmsPortalOrderService portalOrderService) {
        this.portalOrderService = portalOrderService;
    }

    /**
     * @description： 根据购物车信息生成订单
     * com.youkongyi.mall.controller.OmsPortalOrderController.generateOrder
     * @param： orderParam (com.youkongyi.mall.dto.OrderParam)
     * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.String>
     * @author： Aimer
     * @crateDate： 2022/7/25 18:05
     */
    @PostMapping("/generate")
    public ReturnDTO<String> generateOrder(@RequestBody OrderParam orderParam) {
        ReturnDTO<String> returnDTO = new ReturnDTO<>();
        try {
            returnDTO = portalOrderService.generateOrder(orderParam);
        } catch (Exception e) {
            log.error("获取品牌信息集合异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }
}