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

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/userInfo")
    public ApiResult<UserDto> loadUserByUsername() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User user = userService.getById(1);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setUserPassword(null);
        return ApiResult.success(userDto);
    }
}
