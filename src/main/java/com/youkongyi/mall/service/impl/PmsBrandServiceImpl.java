package com.youkongyi.mall.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.youkongyi.mall.common.emum.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.mapper.PmsBrandMapper;
import com.youkongyi.mall.model.PmsBrand;
import com.youkongyi.mall.service.IPmsBrandService;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service
public class PmsBrandServiceImpl implements IPmsBrandService {

    private final PmsBrandMapper pmsBrandMapper;

    @Autowired
    public PmsBrandServiceImpl(PmsBrandMapper pmsBrandMapper) {
        this.pmsBrandMapper = pmsBrandMapper;
    }

    @Override
    public ReturnDTO<PageInfo<PmsBrand>> getBrandForPage(Map<String,String> reqMap) {
        ReturnDTO<PageInfo<PmsBrand>> returnDTO = new ReturnDTO<>();
        JSONObject obj = JSONUtil.parseObj(reqMap);
        PmsBrand entity = obj.toBean(PmsBrand.class);
        int pageNo = obj.getInt("pageNo", 1);
        int pageSize = obj.getInt("pageSize", 10);
        PageHelper.startPage(pageNo, pageSize);
        List<PmsBrand> list = pmsBrandMapper.selectList(entity);
        returnDTO.setCode(ResultCode.SUCCESS.getCode());
        returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
        returnDTO.setData(PageInfo.of(list));
        return returnDTO;
    }

    @Override
    public ReturnDTO<Boolean> createBrand(PmsBrand brand) {
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        returnDTO.setCode(ResultCode.FAILED.getCode());
        returnDTO.setMsg(ResultCode.FAILED.getMessage());
        returnDTO.setData(false);
        int count = pmsBrandMapper.insert(brand);
        if (count == 1) {
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(true);
        }
        return returnDTO;
    }

    @Override
    public ReturnDTO<Boolean> updateBrand(PmsBrand brand) {
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        returnDTO.setCode(ResultCode.FAILED.getCode());
        returnDTO.setMsg(ResultCode.FAILED.getMessage());
        returnDTO.setData(false);
        int count = pmsBrandMapper.updateByPrimaryKey(brand);
        if (count == 1) {
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(true);
        }
        return returnDTO;
    }

    @Override
    public ReturnDTO<Boolean> deleteBrand(Long id) {
        ReturnDTO<Boolean> returnDTO = new ReturnDTO<>();
        returnDTO.setCode(ResultCode.FAILED.getCode());
        returnDTO.setMsg(ResultCode.FAILED.getMessage());
        returnDTO.setData(false);
        int count = pmsBrandMapper.deleteByPrimaryKey(id);
        if (count == 1) {
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(true);
        }
        return returnDTO;
    }

    @Override
    public ReturnDTO<PmsBrand> getBrand(Long id) {
        ReturnDTO<PmsBrand> returnDTO = new ReturnDTO<>();
        Optional<PmsBrand> opt = pmsBrandMapper.selectByPrimaryKey(id);
        returnDTO.setCode(ResultCode.SUCCESS.getCode());
        returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
        opt.ifPresent(returnDTO::setData);
        return returnDTO;
    }
}
