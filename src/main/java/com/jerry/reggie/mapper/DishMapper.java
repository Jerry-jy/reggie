package com.jerry.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerry.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: DishMapper
 * Package: com.jerry.reggie.mapper
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 10:50
 * @Version 1.0
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
