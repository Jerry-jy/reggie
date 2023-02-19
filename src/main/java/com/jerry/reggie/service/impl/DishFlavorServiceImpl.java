package com.jerry.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.reggie.entity.DishFlavor;
import com.jerry.reggie.mapper.DishFlavorMapper;
import com.jerry.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * ClassName: DishFlavorServiceImpl
 * Package: com.jerry.reggie.service.impl
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 19:10
 * @Version 1.0
 */

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
