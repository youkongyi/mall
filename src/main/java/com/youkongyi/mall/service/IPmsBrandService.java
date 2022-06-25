package com.youkongyi.mall.service;

import com.youkongyi.mall.common.util.PagerResult;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.model.PmsBrand;

import java.util.Map;

public interface IPmsBrandService {

    PagerResult<PmsBrand> getBrandForPage(Map<String,String> reqMap);

    ReturnDTO<Boolean> createBrand(PmsBrand brand);

    ReturnDTO<Boolean> updateBrand(PmsBrand brand);

    ReturnDTO<Boolean> deleteBrand(Long id);

    ReturnDTO<PmsBrand> getBrand(Long id);
}
