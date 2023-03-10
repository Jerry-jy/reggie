package com.jerry.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.reggie.dto.SetmealDto;
import com.jerry.reggie.entity.Setmeal;

import java.util.List;

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
     *
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    //根据id查询套餐信息和对应的套餐内容---回显套餐信息
    SetmealDto getByIdWithSetmeal(long id);

    //修改套餐信息，并保存
    void updateWithSetmeal(SetmealDto setmealDto);

    //删除套餐，同时输出套餐和菜品关联的数据
    void removeWithDish(List<Long> ids);

    //修改套餐的 停/启 售状态
    void setStatus(Setmeal setmeal);

    //批量修改套餐的 停/启 售状态
    List<Setmeal> changeSetmealStatus(List<Long> ids);
}
