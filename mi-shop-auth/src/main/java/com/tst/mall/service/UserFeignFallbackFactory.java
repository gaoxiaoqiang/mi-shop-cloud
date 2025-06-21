package com.tst.mall.service;

import com.tst.mall.common.exception.BaseException;
import com.tst.mall.common.response.ApiResult;
import com.tst.mall.model.dto.UserDto;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFeignFallbackFactory  implements FallbackFactory<UserFeignService> {
    @Override
    public UserFeignService create(Throwable cause) {

       return  new  UserFeignService(){

           @Override
           public ApiResult<UserDto> loadUserByUsername(String username) {

               throw  new BaseException(cause.getMessage());

           }

           @Override
           public ApiResult<UserDto> loadUserByUserPhone(String userPhone) {
               throw  new BaseException(cause.getMessage());
           }

           @Override
           public ApiResult<UserDto> loadUserByOpenId(String openId,String columnName ) {
               throw  new BaseException(cause.getMessage());
           }
       };
    }
}
