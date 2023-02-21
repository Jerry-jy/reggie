package com.jerry.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.reggie.common.CustomException;
import com.jerry.reggie.dto.SetmealDto;
import com.jerry.reggie.entity.Setmeal;
import com.jerry.reggie.entity.SetmealDish;
import com.jerry.reggie.mapper.SetmealMapper;
import com.jerry.reggie.service.SetmealDishService;
import com.jerry.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: SetmealServiceImpl
 * Package: com.jerry.reggie.service.impl
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 10:54
 * @Version 1.0
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    SetmealMapper setmealMapper;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息 操作setmeal  执行insert
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联信息，操作setmeal_dish，执行insert
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 根据id查询套餐信息和对应的套餐内容---回显套餐信息
     *
     * @param id
     * @return
     */
    @Override
    public SetmealDto getByIdWithSetmeal(long id) {
        //查询套餐基本信息，从setmeal表查询
        Setmeal setMeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setMeal, setmealDto);

        //查询当前套餐对应的套餐信息，从setmeal_dish表中查
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, setMeal.getId());
        List<SetmealDish> setmealDishes = setmealDishService.list(lambdaQueryWrapper);

        setmealDto.setSetmealDishes(setmealDishes);
        return setmealDto;
    }

    /**
     * 修改套餐信息，并保存
     *
     * @param setmealDto
     */
    @Override
    @Transactional
    public void updateWithSetmeal(SetmealDto setmealDto) {
        //更新 setmeal 表基本信息
        this.updateById(setmealDto);

        //先清理当前套餐对应的套餐信息--- setmeal_dish 表的 delete 操作
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(lambdaQueryWrapper);

        //再添加当前提交过来的套餐数据-- setmeal_dish表的 insert 操作
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐，同时输出套餐和菜品关联的数据
     *
     * @param ids
     */
    @Override
    public void removeWithDish(List<Long> ids) {
        // 先查询套餐状态，确实是否可以删除
        // select count(*) from setmeal where id in (1, 2, 3) and status = 1;
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, ids);
        lambdaQueryWrapper.eq(Setmeal::getStatus, 1);

        int count = this.count(lambdaQueryWrapper);
        if (count > 0) {
            // 如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }


        // 如果可以删除，先删除套餐表中的数据-- setmeal
        this.removeByIds(ids);

        //再删除关系表中的数据-- setmeal_dish
        // delete from setmeal_dish where id in (1, 2, 3);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(queryWrapper);
    }

    /**
     * 批量修改套餐的 停/启 售状态
     * @param ids
     * @return
     */
    @Override
    public List<Setmeal> changeSetmealStatus(List<Long> ids) {
        //改变套餐状态
        //先去数据库查询前端页面传递过来的List
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, ids);

        //找到对应的实体集合
        List<Setmeal> setmealList = setmealMapper.selectList(lambdaQueryWrapper);

        for (Setmeal setmeal : setmealList) {
            if (setmealMapper.selectById(setmeal.getId()).getStatus()== 1) {
                setmealMapper.selectById(setmeal.getId()).setStatus(0);
//                this.updateById(setmeal);
//                setmeal.setStatus(0);
            } else {
                setmealMapper.selectById(setmeal.getId()).setStatus(1);
                this.updateById(setmeal);
            }
        }
        return setmealList;
    }

    /**
     * 修改套餐的 停/启 售状态
     * @param setmeal
     */
    @Override
    public void setStatus(Setmeal setmeal) {
        if (setmeal.getStatus() == 1) {
            setmeal.setStatus(0);
        } else {
            setmeal.setStatus(1);
        }
        this.updateById(setmeal);
    }
}
