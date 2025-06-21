package com.tst.mall.common.constant;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Oauth2Type {

    WX("wx", "wx_id"),
    QQ("qq", "qq_id"),
    GIT_HUB("github", "github_id"),
    GIT_EE("git_ee", "gitee_id");

    private final String registrationId;
    private final String columnName;

    Oauth2Type(String registrationId,String columnName) {
        this.columnName = columnName;
        this.registrationId=registrationId;
    }

     // 缓存映射，提高查找效率
     private static final Map<String, Oauth2Type> REGISTRATION_ID_MAP;
     private static final Map<String, Oauth2Type> COLUMN_NAME_MAP;

     static {
         // 初始化映射
         REGISTRATION_ID_MAP = Arrays.stream(values())
                 .collect(Collectors.toMap(
                         Oauth2Type::getRegistrationId,
                         Function.identity()
                 ));

         COLUMN_NAME_MAP = Arrays.stream(values())
                 .collect(Collectors.toMap(
                         Oauth2Type::getColumnName,
                         Function.identity()
                 ));
     }

     /**
      * 根据 registrationId 获取对应的枚举实例
      *
      * @param registrationId 注册ID
      * @return 对应的枚举实例
      * @throws IllegalArgumentException 如果没有匹配的枚举值
      */
     public static Oauth2Type fromRegistrationId(String registrationId) {
         Oauth2Type type = REGISTRATION_ID_MAP.get(registrationId);
         if (type == null) {
             throw new IllegalArgumentException("Invalid registrationId: " + registrationId);
         }
         return type;
     }

    public String getColumnName() {
        return columnName;
    }

    public  String getRegistrationId() {
        return registrationId;
    }




}
