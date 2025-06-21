package com.tst.mall.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author gxq
 * @since 2025-06-11
 */
@Getter
@Setter
@TableName("tb_mall_user")
@Schema(name = "User", description = "$!{table.comment}")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户主键id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @Schema(description = "登陆名称(默认为手机号)")
    private String userName;

    @Schema(description = "手机号")
    private String userPhone;

    @Schema(description = "加密后的密码")
    private String userPassword;

    @Schema(description = "qq_id")
    private String qqId;

    @Schema(description = "wx_id")
    private String wxId;

    @Schema(description = "锁定标识字段(0-未锁定 1-已锁定)")
    private Byte lockedFlag;

    @Schema(description = "注销标识字段(0-正常 1-已注销)")
    private Byte deleteFlag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
