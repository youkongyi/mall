package com.youkongyi.mall.common.util;

import com.github.pagehelper.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
  * @description： 通用分页结果信息
  *     com.youkongyi.mall.common.util.PagerResult
  * @author： Aimer
  * @crateDate： 2022/06/25 11:07
  */
@Setter
@Getter
public class PagerResult<T> {
    /**
     * 状态码
     */
    private int code;
    /**
     * 状态消息
     */
    private String msg;
    /**
     * 当前页码
     */
    private Integer currentPage;
    /*
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 总记录数
     */
    private Long totalRecord;
    /**
     * 返回结果
     */
    private List<T> rows;

    public void setRows(List<T> rows) {
        this.rows = rows;
        if(rows instanceof Page){
            Page<T> page = (Page<T>) rows;
            this.currentPage = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.totalPage = page.getPages();
            this.totalRecord = page.getTotal();
        }
    }
}
