package com.youkongyi.mall.common.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
  * @description： 分页信息
  *     com.youkongyi.mall.common.util.Pager
  * @author： Aimer
  * @crateDate： 2022/06/27 09:32
  */
@Setter
@Getter
public class Pager {
    /**
     * 页码
     */
    @Schema(description = "页码")
    private int pageNum = 1;
    /**
     * 页面大小
     */
    @Schema(description = "页面大小")
    private int pageSize = 10;
    /**
     * 起始行
     */
    @Schema(description = "起始行")
    private long startRow;
    /**
     * 结束行
     */
    @Schema(description = "结束行")
    private long endRow;
}
