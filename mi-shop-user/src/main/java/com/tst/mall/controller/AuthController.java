package com.tst.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tst.mall.common.response.ApiResult;
import com.tst.mall.model.dto.UserDto;
import com.tst.mall.model.entity.User;
import com.tst.mall.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gxq
 * @since 2025-06-11
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    UserService userService;

    @RequestMapping("/username")
    public ApiResult<UserDto> loadUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User user = userService.getOne(queryWrapper);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return ApiResult.success(userDto);
    }

    @RequestMapping("/userPhone")
    public ApiResult<UserDto> loadUserByUserPhone(String userPhone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", userPhone);
        User user = userService.getOne(queryWrapper);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return ApiResult.success(userDto);
    }

    @RequestMapping("/openId")
    public ApiResult<UserDto> loadUserByOpenId(String openId,String columnName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(columnName, openId);
        User user = userService.getOne(queryWrapper);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return ApiResult.success(userDto);
    }

}
