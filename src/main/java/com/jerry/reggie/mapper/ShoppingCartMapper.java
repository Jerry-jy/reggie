package com.jerry.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerry.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: ShoppingCartMapper
 * Package: com.jerry.reggie.mapper
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-22 10:12
 * @Version 1.0
 */

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
