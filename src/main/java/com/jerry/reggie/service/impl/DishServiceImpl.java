package com.jerry.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.reggie.dto.DishDto;
import com.jerry.reggie.entity.Dish;
import com.jerry.reggie.entity.DishFlavor;
import com.jerry.reggie.mapper.DishFlavorMapper;
import com.jerry.reggie.mapper.DishMapper;
import com.jerry.reggie.service.DishFlavorService;
import com.jerry.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: DishServiceImpl
 * Package: com.jerry.reggie.service.impl
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 10:53
 * @Version 1.0
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品同时保存口味数据
     * @param dishDto
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId();

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味到到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }
}
