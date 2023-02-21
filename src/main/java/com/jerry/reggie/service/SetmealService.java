package com.jerry.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.reggie.dto.SetmealDto;
import com.jerry.reggie.entity.Setmeal;

/**
 * ClassName: SetMealService
 * Package: com.jerry.reggie.service
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 10:52
 * @Version 1.0
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);
}
