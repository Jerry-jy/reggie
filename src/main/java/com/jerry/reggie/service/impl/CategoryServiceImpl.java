package com.jerry.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.reggie.entity.Category;
import com.jerry.reggie.mapper.CategoryMapper;
import com.jerry.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * ClassName: CategoryServiceImpl
 * Package: com.jerry.reggie.service.impl
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 9:30
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
