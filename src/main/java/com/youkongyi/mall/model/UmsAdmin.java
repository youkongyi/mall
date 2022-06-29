package com.youkongyi.mall.model;

import io.mybatis.provider.Entity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity.Table("ums_admin")
public class UmsAdmin {

    @Schema(description = "主键",hidden = true)
    @Entity.Column(id = true)
    private String id;

    @Schema(description = "用户名")
    @Entity.Column
    private String username;

    @Schema(description = "密码")
    @Entity.Column
    private String password;

    @Schema(description = "头像")
    @Entity.Column
    private String icon;

    @Schema(description = "邮箱")
    @Entity.Column
    private String email;

    @Schema(description = "昵称")
    @Entity.Column("nick_name")
    private String nickName;

    @Schema(description = "备注信息")
    @Entity.Column
    private String note;

    @Schema(description = "创建时间")
    @Entity.Column("create_time")
    private String createTime;

    @Schema(description = "最后登录时间")
    @Entity.Column("login_time")
    private String loginTime;

    @Schema(description = "帐号启用状态：0->禁用；1->启用")
    @Entity.Column
    private String status;
}
