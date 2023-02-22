package com.jerry.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.reggie.entity.ShoppingCart;
import com.jerry.reggie.mapper.ShoppingCartMapper;
import com.jerry.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: ShoppingCartServiceImpl
 * Package: com.jerry.reggie.service.impl
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-22 10:13
 * @Version 1.0
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

}
