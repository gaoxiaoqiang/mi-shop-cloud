package com.tst.mall.service;


import com.tst.mall.common.constant.Oauth2Type;
import com.tst.mall.common.response.ApiResult;
import com.tst.mall.model.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mi-shop-user",fallbackFactory = UserFeignFallbackFactory.class)
public interface UserFeignService {

    @RequestMapping( value = "/auth/username")
    ApiResult<UserDto> loadUserByUsername(@RequestParam("username") String username);


    @RequestMapping( value = "/auth/userPhone")
    ApiResult<UserDto> loadUserByUserPhone(@RequestParam("userPhone") String userPhone);


    @RequestMapping( value = "/auth/openId")
    ApiResult<UserDto> loadUserByOpenId(@RequestParam("openId") String openId , @RequestParam("columnName") String columnName);

}
