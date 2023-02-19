package com.jerry.reggie.controller;

import com.jerry.reggie.common.R;
import com.jerry.reggie.dto.DishDto;
import com.jerry.reggie.entity.Dish;
import com.jerry.reggie.service.DishFlavorService;
import com.jerry.reggie.service.DishService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: DishController
 * Package: com.jerry.reggie.controller
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 19:11
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    DishService dishService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> addMeal(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);
        return R.success("保存成功");
    }
}
