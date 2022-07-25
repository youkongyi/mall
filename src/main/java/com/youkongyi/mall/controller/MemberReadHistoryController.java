package com.youkongyi.mall.controller;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.nosql.mongodb.document.MemberReadHistory;
import com.youkongyi.mall.service.IMemberReadHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
  * @description： 会员商品浏览记录管理
  *     com.youkongyi.mall.controller.MemberReadHistoryController
  * @author： Aimer
  * @crateDate： 2022/7/22 10:02
  */
@Slf4j
@RestController
@RequestMapping("/member/readHistory")
@Tag(name = "浏览记录", description = "会员商品浏览记录管理")
public class MemberReadHistoryController {

    private final IMemberReadHistoryService service;

    @Autowired
    public MemberReadHistoryController(IMemberReadHistoryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ReturnDTO<Integer> create(@RequestBody MemberReadHistory readHistory){
        ReturnDTO<Integer> returnDTO = new ReturnDTO<>();
        try {
            Integer result = service.create(readHistory);
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(result);
        }catch (Exception e){
            log.error("创建浏览记录异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }

    @PostMapping("/delete")
    public ReturnDTO<Integer> delete(@RequestParam List<String> ids){
        ReturnDTO<Integer> returnDTO = new ReturnDTO<>();
        try {
            Integer result = service.delete(ids);
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(result);
        }catch (Exception e){
            log.error("删除浏览记录异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }

    @PostMapping("/list/{id}")
    public ReturnDTO<List<MemberReadHistory>> list(@PathVariable Long id){
        ReturnDTO<List<MemberReadHistory>> returnDTO = new ReturnDTO<>();
        try {
            List<MemberReadHistory> result = service.list(id);
            returnDTO.setCode(ResultCode.SUCCESS.getCode());
            returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
            returnDTO.setData(result);
        }catch (Exception e){
            log.error("获取浏览记录异常: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }
}
