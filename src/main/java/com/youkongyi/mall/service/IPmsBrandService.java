package com.youkongyi.mall.service;

import com.youkongyi.mall.common.util.Pager;
import com.youkongyi.mall.common.util.PagerResult;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.model.PmsBrand;

public interface IPmsBrandService {

    PagerResult<PmsBrand> getBrandForPage(PmsBrand brand, Pager pager);

    ReturnDTO<Boolean> createBrand(PmsBrand brand);

    ReturnDTO<Boolean> updateBrand(PmsBrand brand);

    ReturnDTO<Boolean> deleteBrand(Long id);

    ReturnDTO<PmsBrand> getBrand(Long id);
}
