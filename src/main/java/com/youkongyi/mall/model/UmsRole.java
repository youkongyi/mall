package com.youkongyi.mall.model;

import io.mybatis.provider.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
  * @description： 后台用户角色表
  *     com.youkongyi.mall.model.UmsRole
  * @author： Aimer
  * @crateDate： 2022/10/26 20:07
  */
@Setter
@Getter
@Entity.Table("ums_role")
public class UmsRole {

    @Schema(description = "主键")
    @Entity.Column(id = true)
    private Long id;

    @Schema(description = "名称")
    @Entity.Column
    private String name;

    @Schema(description = "描述")
    @Entity.Column
    private String description;

    @Schema(description = "后台用户数量")
    @Entity.Column("admin_count")
    private Integer adminCount;

    @Schema(description = "创建时间")
    @Entity.Column("create_time")
    private String createTime;

    @Schema(description = "启用状态：0->禁用；1->启用")
    @Entity.Column
    private Integer status;

    @Schema(description = "排序")
    @Entity.Column
    private Integer sort;
}
