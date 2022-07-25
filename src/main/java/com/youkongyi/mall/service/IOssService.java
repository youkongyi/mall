package com.youkongyi.mall.service;

import com.youkongyi.mall.dto.OssCallbackResult;
import com.youkongyi.mall.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
  * @description： OSS上传管理Service
  *     com.youkongyi.mall.service.IOssService.
  * @author： Aimer
  * @crateDate： 2022/7/25 19:11
  */
public interface IOssService {

    /**
      * @description： oss上传策略生成
      *     com.youkongyi.mall.service.IOssService.policy
      * @return： com.youkongyi.mall.dto.OssPolicyResult
      * @author： Aimer
      * @crateDate： 2022/7/25 19:12
      */
    OssPolicyResult policy();

    /**
      * @description： oss上传成功回调
      *     com.youkongyi.mall.service.IOssService.callback
      * @param： request (javax.servlet.http.HttpServletRequest)
      * @return： com.youkongyi.mall.dto.OssCallbackResult
      * @author： Aimer
      * @crateDate： 2022/7/25 19:12
      */
    OssCallbackResult callback(HttpServletRequest request);
}
