package com.tst.mall.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long userId;
    private String userPassword;
    private String userName;
    private String userImg;
    private String userPhone;
    private String userEmail;
    private String qqId;
    private String wxId;
    private String githubId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Byte lockedFlag;
    private String token;


}
