package com.youkongyi.mall.controller;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.PagerResult;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.nosql.elasticsearch.document.EsProduct;
import com.youkongyi.mall.service.IEsProductService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/esProduct")
@Tag(name = "搜索商品", description = "搜索商品管理")
public class EsProductController {

    private final IEsProductService productService;

    @Autowired
    public EsProductController(IEsProductService productService) {
        this.productService = productService;
    }

    /**
      * @description： 导入所有数据库中商品到ES
      *     com.youkongyi.mall.controller.EsProductController.importAllList
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.Integer>
      * @author： Aimer
      * @crateDate： 2022/7/15 9:43
      */
    @Operation(summary = "2001_导入所有数据库中商品到ES", description = "导入所有数据库中商品到ES")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = Integer.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
    })
    @PostMapping("/importAll")
    public ReturnDTO<Integer> importAllList() {
        ReturnDTO<Integer> returnDTO = new ReturnDTO<>();
        try {
            int count = productService.importAll();
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(count);
        } catch (Exception e){
            log.error("Es导入商品信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }

    @Operation(summary = "2002_分页检索商品信息", description = "分页检索商品信息")
    @Parameters({
            @Parameter(name = "keyword", description = "关键字", in = ParameterIn.QUERY)
            ,@Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY)
            ,@Parameter(name = "pageSize", description = "页面大小", in = ParameterIn.QUERY)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = EsProduct.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
    })
    @GetMapping("/search/simple")
    public PagerResult<EsProduct> search(@RequestParam(required = false) String keyword,
                                         @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        PagerResult<EsProduct> returnDTO = new PagerResult<>();
        try {
            List<EsProduct> esProductPage = productService.search(keyword, pageNum, pageSize);
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setRows(esProductPage);
        }catch (Exception e){
            log.error("Es查询商品信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }

    @Operation(summary = "2003_根据商品id删除信息", description = "根据商品id删除信息")
    @Parameters({
            @Parameter(name = "id", description = "主键", in = ParameterIn.PATH)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = EsProduct.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
    })
    @GetMapping("/delete/{id}")
    public ReturnDTO<Boolean> delete(@PathVariable String id) {
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        try {
            productService.delete(id);
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(Boolean.TRUE);
        }catch (Exception e){
            log.error("Es删除商品信息异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }

    @Operation(summary = "2004_根据DocumentId批量删除商品", description = "根据DocumentId批量删除商品")
    @Parameters({
            @Parameter(name = "ids", description = "主键集合",example = "1,2,3", in = ParameterIn.QUERY)
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
    @PostMapping("/delete/batch")
    public ReturnDTO<Boolean> delete(@RequestParam("ids") List<String> ids) {
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        try {
            productService.delete(ids);
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(Boolean.TRUE);
        } catch (Exception e){
            log.error("Es批量删除商品异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }

    @Operation(summary = "2005_根据id创建商品", description = "根据id创建商品")
    @Parameters({
            @Parameter(name = "id", description = "主键", in = ParameterIn.PATH)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = EsProduct.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
    })
    @PostMapping("/create/{id}")
    public ReturnDTO<EsProduct> create(@PathVariable Long id) {
        ReturnDTO<EsProduct> returnDTO = new ReturnDTO<>();
        try {
            returnDTO = productService.create(id);
        } catch (Exception e){
            log.error("Es创建商品异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }
}
