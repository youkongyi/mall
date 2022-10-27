package com.youkongyi.mall.service.ums;

import com.youkongyi.mall.common.util.PagerResult;
import com.youkongyi.mall.model.UmsAdmin;

import java.util.Map;

/**
  * @description： 操作员管理Controller
  *     com.youkongyi.mall.controller.ums.IOperatorManageService.
  * @author： Aimer
  * @crateDate： 2022/10/27 14:49
  */
public interface IOperatorManageService {

    /**
      * @description： 操作员列表分页查询
      *     com.youkongyi.mall.service.ums.IOperatorManageService.queryOperatorForPage
      * @param： reqMap (java.util.Map<java.lang.String,java.lang.Object>)
      * @return： com.youkongyi.mall.common.util.PagerResult<com.youkongyi.mall.model.UmsAdmin>
      * @author： Aimer
      * @crateDate： 2022/10/27 16:20
      */
    PagerResult<UmsAdmin> queryOperatorForPage(Map<String, Object> reqMap);
}
