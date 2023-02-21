package com.jerry.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.reggie.entity.User;
import com.jerry.reggie.mapper.UserMapper;
import com.jerry.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserServiceImpl
 * Package: com.jerry.reggie.service.impl
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-21 17:59
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;


}
