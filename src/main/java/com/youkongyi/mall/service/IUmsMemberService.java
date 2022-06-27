package com.youkongyi.mall.service;

import com.youkongyi.mall.common.util.ReturnDTO;

/**
  * @description： 会员登录注册管理Service
  *     com.youkongyi.mall.service.IUmsMemberService
  * @author： Aimer
  * @crateDate： 2022/06/27 13:50
  */
public interface IUmsMemberService {

    /**
      * @description： 生成验证码信息
      *     com.youkongyi.mall.service.IUmsMemberService.generateAuthCode
      * @param： telephone (java.lang.String)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.String>
      * @author： Aimer
      * @crateDate： 2022/06/27 13:49
      */
    ReturnDTO<String> generateAuthCode(String telephone);

    /**
      * @description： 校验相应验证码
      *     com.youkongyi.mall.service.IUmsMemberService.verifyAuthCode
      * @param： telephone (java.lang.String)
      * @param： authCode (java.lang.String)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.String>
      * @author： Aimer
      * @crateDate： 2022/06/27 13:49
      */
    ReturnDTO<String> verifyAuthCode(String telephone,String authCode);

}
