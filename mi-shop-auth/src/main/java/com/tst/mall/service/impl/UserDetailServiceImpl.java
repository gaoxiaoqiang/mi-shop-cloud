//package com.tst.mall.service.impl;
//
//import com.tst.mall.common.exception.BaseException;
//
//import com.tst.mall.common.response.ApiResult;
//import com.tst.mall.model.dto.UserDetailsImpl;
//import com.tst.mall.model.dto.UserDto;
//import com.tst.mall.service.UserFeignService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class UserDetailServiceImpl implements UserDetailsService{
//
//    @Autowired
//    UserFeignService userFeignService;
//
//
//     @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         ApiResult<UserDto> result = userFeignService.loadUserByUsername(username);
//        if (result == null) {
//            throw new BaseException("用户名不存在");
//        }
//        return new UserDetailsImpl(result.getData());
//    }
//
//
//}
