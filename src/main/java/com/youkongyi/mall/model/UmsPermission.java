package com.youkongyi.mall.model;

import io.mybatis.provider.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity.Table("ums_permission")
public class UmsPermission {

    @Schema(description = "主键")
    @Entity.Column(id = true)
    private String id;

    @Schema(description = "父级权限编码")
    @Entity.Column
    private String pid;

    @Schema(description = "名称")
    @Entity.Column
    private String name;

    @Schema(description = "权限值")
    @Entity.Column
    private String value;

    @Schema(description = "图标")
    @Entity.Column
    private String icon;

    @Schema(description = "权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    @Entity.Column
    private String type;

    @Schema(description = "前端资源路径")
    @Entity.Column
    private String uri;

    @Schema(description = "启用状态；0->禁用；1->启用")
    @Entity.Column
    private String status;

    @Schema(description = "创建时间")
    @Entity.Column("create_time")
    private String create_time;

    @Schema(description = "排序")
    @Entity.Column
    private String sort;
}
