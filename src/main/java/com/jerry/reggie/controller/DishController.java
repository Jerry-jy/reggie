package com.jerry.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jerry.reggie.common.R;
import com.jerry.reggie.dto.DishDto;
import com.jerry.reggie.entity.Category;
import com.jerry.reggie.entity.Dish;
import com.jerry.reggie.service.CategoryService;
import com.jerry.reggie.service.DishFlavorService;
import com.jerry.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    CategoryService categoryService;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> addMeal(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);
        return R.success("保存成功");
    }

    /**
     * 菜品信息--分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        lambdaQueryWrapper.like(name != null, Dish::getName, name);
        //添加过滤条件
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo, lambdaQueryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            //对象拷贝
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();//分类id
            Category category = categoryService.getById(categoryId);//分类对象

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getMealById(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> updateMeal(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());

        dishService.updateWithFlavor(dishDto);
        return R.success("保存菜品成功");
    }


    /**
     * 修改菜品的 停/启 售状态
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<Dish> status(long ids) {
        Dish dish = dishService.getById(ids);
        dishService.setStatus(dish);
        return R.success(dish);
    }
//    public R<Dish> status(long[] ids) {
//        Dish dish = dishService.getById(ids);
//        dishService.setStatus(dish);
//        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        for (long id : ids) {
//            lambdaQueryWrapper.eq(Dish::getId, id);
//            dishService.update(dish, lambdaQueryWrapper);
//        }
//
//        return R.success(dish);
//    }


    /**
     * 菜品管理--批量删除菜品--跟单个删除一样的，复用同一份代码
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long[] ids){
        for (Long id : ids) {
            dishService.delete(id);
        }
        return R.success("删除菜品成功！");
    }
}
