package com.youkongyi.mall.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.service.IUmsMemberService;
import com.youkongyi.mall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
  * @description： 会员登录注册管理Service
  *     com.youkongyi.mall.service.impl.UmsMemberServiceImpl
  * @author： Aimer
  * @crateDate： 2022/06/27 13:50
  */
@Service
public class UmsMemberServiceImpl implements IUmsMemberService {

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Value("${redis.key.prefix.auth-code}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    /**
      * @description： 生成验证码信息
      *     com.youkongyi.mall.service.impl.UmsMemberServiceImpl.generateAuthCode
      * @param： telephone (java.lang.String)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.String>
      * @author： Aimer
      * @crateDate： 2022/06/27 13:51
      */
    @Override
    public ReturnDTO<String> generateAuthCode(String telephone) {
        ReturnDTO<String> ret = new ReturnDTO<>();
        // 生成6位数验证码
        String str = RandomUtil.randomNumbers(6);
        // 设置缓存
        redisUtil.set(REDIS_KEY_PREFIX_AUTH_CODE + telephone, str, TimeUnit.MINUTES.toSeconds(5));
        ret.setCode(ResultCode.SUCCESS.getCode());
        ret.setMsg(ResultCode.SUCCESS.getMessage());
        ret.setData(str);
        return ret;
    }

    /**
      * @description： 校验相应验证码
      *     com.youkongyi.mall.service.impl.UmsMemberServiceImpl.verifyAuthCode
      * @param： telephone (java.lang.String)
      * @param： authCode (java.lang.String)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.String>
      * @author： Aimer
      * @crateDate： 2022/06/27 13:51
      */
    @Override
    public ReturnDTO<String> verifyAuthCode(String telephone,String authCode) {
        ReturnDTO<String> ret = new ReturnDTO<>();
        ret.setCode(ResultCode.SUCCESS.getCode());
        ret.setMsg(ResultCode.SUCCESS.getMessage());
        ret.setData("验证码不正确");
        String realAuthCode = redisUtil.get(REDIS_KEY_PREFIX_AUTH_CODE + telephone, String.class);
        if(StringUtils.equals(realAuthCode, authCode)){
            ret.setData("验证码校验成功");
            // 校验成功后删除
            redisUtil.del(REDIS_KEY_PREFIX_AUTH_CODE + telephone);
        }
        return ret;
    }
}
