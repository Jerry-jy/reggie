package com.jerry.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerry.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: CategoryMapper
 * Package: com.jerry.reggie.mapper
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 9:28
 * @Version 1.0
 */

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
