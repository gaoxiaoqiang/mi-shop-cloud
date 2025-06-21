package com.tst.mall.controller;


import com.tst.mall.common.response.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {




    @GetMapping("/user-info")
    public ApiResult<Map<String, Object>> getUserInfo() {



        return ApiResult.success();
    }










}
