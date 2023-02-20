package com.jerry.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.reggie.dto.DishDto;
import com.jerry.reggie.entity.Dish;

/**
 * ClassName: DishService
 * Package: com.jerry.reggie.service
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 10:52
 * @Version 1.0
 */
public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要同时操作两张表，dish和dishFlavor表
    public void saveWithFlavor(DishDto dishDto);

    //根据id来查询
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    //修改菜品的停/启售状态
    public void setStatus(Dish dish);

}
