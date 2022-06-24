package com.youkongyi.mall.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.model.PmsBrand;
import com.youkongyi.mall.service.IPmsBrandService;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/brand")
@Tag(name = "PmsBrandController",description = "商品品牌管理")
public class PmsBrandController {

    private final IPmsBrandService pmsBrandService;
    @Autowired
    public PmsBrandController(IPmsBrandService pmsBrandService) {
        this.pmsBrandService = pmsBrandService;
    }

    /**
      * @description： 分页获取品牌信息
      *     com.youkongyi.mall.controller.PmsBrandController.getBrandForPage
      * @param： reqStr (java.lang.String)
      * @return： com.youkongyi.mall.dto.ReturnDTO<com.github.pagehelper.PageInfo<com.youkongyi.mall.model.PmsBrand>>
      * @author： Aimer
      * @crateDate： 2022/05/30 14:11
      */
    @Operation(summary = "1001_分页获取品牌信息", description = "获取所有品牌列表")
    @GetMapping("/page")
    public ReturnDTO<PageInfo<PmsBrand>> getBrandForPage(@RequestParam Map<String,String> reqMap) {
        ReturnDTO<PageInfo<PmsBrand>> returnDTO = new ReturnDTO<>();
        try {
            return pmsBrandService.getBrandForPage(reqMap);
        } catch (Exception e) {
            log.error("获取品牌信息集合异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg("获取品牌信息集合异常!");
        }
        return returnDTO;
    }

    /**
      * @description： 保存品牌信息
      *     com.youkongyi.mall.controller.PmsBrandController.createBrand
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.Boolean>
      * @author： Aimer
      * @crateDate： 2022/05/30 16:09
      */
    @PostMapping("/save")
    public ReturnDTO<Boolean> createBrand(@RequestBody PmsBrand brand){
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        try {
            return pmsBrandService.createBrand(brand);
        } catch (Exception e) {
            log.error("保存品牌信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg("保存品牌信息异常!");
        }
        return returnDTO;
    }

    /**
      * @description： 依据主键编码更新品牌信息
      *     com.youkongyi.mall.controller.PmsBrandController.updateBrand
      * @param： brand (com.youkongyi.mall.model.PmsBrand)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.Boolean>
      * @author： Aimer
      * @crateDate： 2022/05/30 16:12
      */
    @PostMapping("/update")
    public ReturnDTO<Boolean> updateBrand(@RequestBody PmsBrand brand){
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        if(StrUtil.isBlank(brand.getId())){
            returnDTO.setCode(ResultCode.VALIDATE_FAILED.getCode());
            returnDTO.setMsg(ResultCode.VALIDATE_FAILED.getMessage());
            return returnDTO;
        }
        try {
            return pmsBrandService.updateBrand(brand);
        } catch (Exception e) {
            log.error("更新品牌信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg("更新品牌信息异常!");
        }
        return returnDTO;
    }

    /**
      * @description： 依据主键编码删除品牌信息
      *     com.youkongyi.mall.controller.PmsBrandController.deleteBrand
      * @param： id (java.lang.Long) 
      * @return： com.youkongyi.mall.common.util.ReturnDTO<com.youkongyi.mall.model.Boolean>
      * @author： Aimer
      * @crateDate： 2022/05/30 16:13
      */
    @GetMapping("/delete/{id}")
    public ReturnDTO<Boolean> deleteBrand(@PathVariable("id") Long id) {
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        try {
            return pmsBrandService.deleteBrand(id);
        } catch (Exception e){
            log.error("删除品牌信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg("删除品牌信息异常!");
        }
        return returnDTO;
    }

    /**
      * @description： 依据主键编码获取指定品牌信息详情
      *     com.youkongyi.mall.controller.PmsBrandController.getBrand
      * @param： id (java.lang.Long)
      * @return： com.youkongyi.mall.dto.ReturnDTO<com.youkongyi.mall.model.PmsBrand>
      * @author： Aimer
      * @crateDate： 2022/05/30 15:34
      */
    @GetMapping("/find/{id}")
    public ReturnDTO<PmsBrand> getBrand(@PathVariable("id") Long id) {
        ReturnDTO<PmsBrand> returnDTO = new ReturnDTO<>();
        try {
            return pmsBrandService.getBrand(id);
        } catch (Exception e){
            log.error("获取品牌信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg("获取品牌信息异常!");
        }
        return returnDTO;
    }


}
