package com.youkongyi.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.Pager;
import com.youkongyi.mall.common.util.PagerResult;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.model.PmsBrand;
import com.youkongyi.mall.service.IPmsBrandService;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/brand")
@Tag(name = "品牌管理",description = "商品品牌管理")
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
    @Operation(summary = "1001_分页获取品牌信息", description = "分页获取品牌信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY)
            ,@Parameter(name = "pageSize", description = "页面大小", in = ParameterIn.QUERY)
            ,@Parameter(name = "id", description = "主键", in = ParameterIn.QUERY)
            ,@Parameter(name = "name", description = "品牌名称", in = ParameterIn.QUERY)
            ,@Parameter(name = "firstLetter", description = "首字母", in = ParameterIn.QUERY)
//            ,@Parameter(name = "sort", description = "排序", in = ParameterIn.QUERY)
//            ,@Parameter(name = "factoryStatus", description = "是否为品牌制造商", in = ParameterIn.QUERY)
//            ,@Parameter(name = "showStatus", description = "是否显示", in = ParameterIn.QUERY)
//            ,@Parameter(name = "productCount", description = "产品数量", in = ParameterIn.QUERY)
//            ,@Parameter(name = "productCommentCount", description = "产品评论数量", in = ParameterIn.QUERY)
//            ,@Parameter(name = "logo", description = "品牌logo", in = ParameterIn.QUERY)
//            ,@Parameter(name = "bigPic", description = "专区大图", in = ParameterIn.QUERY)
//            ,@Parameter(name = "brandStory", description = "品牌故事", in = ParameterIn.QUERY)
            ,@Parameter(name = "brand",hidden = true, in = ParameterIn.QUERY)
            ,@Parameter(name = "pager",hidden = true, in = ParameterIn.QUERY)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = PmsBrand.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/page")
    public PagerResult<PmsBrand> getBrandForPage(PmsBrand brand, Pager pager) {
        PagerResult<PmsBrand> returnDTO = new PagerResult<>();
        try {
            return pmsBrandService.getBrandForPage(brand, pager);
        } catch (Exception e) {
            log.error("获取品牌信息集合异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
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
    @Operation(summary = "1002_保存品牌信息", description = "保存品牌信息")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "品牌信息", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PmsBrand.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = Boolean.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                  content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/save")
    public ReturnDTO<Boolean> createBrand(@RequestBody PmsBrand brand){
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        try {
            return pmsBrandService.createBrand(brand);
        } catch (Exception e) {
            log.error("保存品牌信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
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
    @Operation(summary = "1003_依据主键编码更新品牌信息", description = "依据主键编码更新品牌信息")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "品牌信息", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PmsBrand.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = Boolean.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
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
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
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
    @Operation(summary = "1004_依据主键编码删除品牌信息", description = "依据主键编码删除品牌信息")
    @Parameters({
            @Parameter(name = "id", description = "主键", in = ParameterIn.PATH)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = Boolean.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/delete/{id}")
    public ReturnDTO<Boolean> deleteBrand(@PathVariable("id") Long id) {
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        try {
            return pmsBrandService.deleteBrand(id);
        } catch (Exception e){
            log.error("删除品牌信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
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
    @Operation(summary = "1005_依据主键编码获取指定品牌信息详情", description = "依据主键编码获取指定品牌信息详情")
    @Parameters({
            @Parameter(name = "id", description = "主键", in = ParameterIn.PATH)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = PmsBrand.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                 content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                 content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/find/{id}")
    public ReturnDTO<PmsBrand> getBrand(@PathVariable("id") Long id) {
        ReturnDTO<PmsBrand> returnDTO = new ReturnDTO<>();
        try {
            return pmsBrandService.getBrand(id);
        } catch (Exception e){
            log.error("获取品牌信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }
}
