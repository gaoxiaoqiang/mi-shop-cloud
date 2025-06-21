//package com.tst.mall.util;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//public class BCryptPasswordUtil {
//
//
//    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//    /**
//     * 加密明文密码
//     */
//    public static String encode(String rawPassword) {
//        return encoder.encode(rawPassword);
//    }
//
//    /**
//     * 校验明文密码和加密后密码是否匹配
//     */
//    public static boolean matches(String rawPassword, String encodedPassword) {
//        return encoder.matches(rawPassword, encodedPassword);
//    }
//
//    // 测试用main方法
//    public static void main(String[] args) {
//        String raw = "123456";
//        String encoded = encode(raw);
//        System.out.println("加密后: " + encoded);
//        System.out.println("校验: " + matches(raw, encoded));
//    }
//}
