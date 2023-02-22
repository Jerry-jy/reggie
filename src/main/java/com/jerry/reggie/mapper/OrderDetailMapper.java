package com.jerry.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerry.reggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: OrderDetailMapper
 * Package: com.jerry.reggie.mapper
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-22 13:54
 * @Version 1.0
 */

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
