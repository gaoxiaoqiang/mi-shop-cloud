package com.tst.mall.service.impl;

import com.tst.mall.model.entity.User;
import com.tst.mall.mapper.UserMapper;
import com.tst.mall.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gxq
 * @since 2025-06-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
