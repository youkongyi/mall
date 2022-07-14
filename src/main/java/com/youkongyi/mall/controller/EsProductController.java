package com.youkongyi.mall.controller;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.PagerResult;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.nosql.elasticsearch.document.EsProduct;
import com.youkongyi.mall.service.IEsProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
