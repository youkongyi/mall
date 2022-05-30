package com.youkongyi.mall.controller;

import com.youkongyi.mall.service.IPmsBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    private final IPmsBrandService pmsBrandService;
    @Autowired
    public PmsBrandController(IPmsBrandService pmsBrandService) {
        this.pmsBrandService = pmsBrandService;
    }


}
