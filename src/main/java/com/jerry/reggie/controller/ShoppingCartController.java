package com.jerry.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jerry.reggie.common.BaseContext;
import com.jerry.reggie.common.R;
import com.jerry.reggie.entity.ShoppingCart;
import com.jerry.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: ShoppingCartController
 * Package: com.jerry.reggie.controller
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-22 10:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据封装，{}", shoppingCart.toString());

        // 设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);


        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());

        if (dishId != null) {
            // 添加到购物车的是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // 添加到购物车的是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        // 查询当前菜品或套餐是否在购物车中，
        ShoppingCart cart = shoppingCartService.getOne(lambdaQueryWrapper);

        if (cart != null) {
            //如果已经存在，就在原来数量基础上加一
            Integer number = cart.getNumber();
            cart.setNumber(number + 1);
            shoppingCartService.updateById(cart);
        } else {
            // 如果不存在，则x添加到购物车，数量默认就是 1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cart = shoppingCart;
        }

        return R.success(cart);
    }

    /**
     * 减少购物车的菜品
     *
     * @return
     */
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据封装，{}", shoppingCart.toString());

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        if (shoppingCart.getDishId() != null) {
            // 减少到购物车的是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            // 减少到购物车的是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        // 查询当前菜品或套餐是否在购物车中，
        ShoppingCart cart = shoppingCartService.getOne(lambdaQueryWrapper);

        //如果已经存在并且数量 > 1，就在原来数量基础上 - 1
        if ((cart.getNumber() > 1)) {
            Integer number = cart.getNumber();
            cart.setNumber(number - 1);
            shoppingCartService.updateById(cart);
        } else {
            //移除改菜品或套餐
            shoppingCartService.remove(lambdaQueryWrapper);
        }
        return R.success(cart);
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        shoppingCartService.remove(lambdaQueryWrapper);

        return R.success("清空购物车成功");
    }


    /**
     * 查看购物车
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        log.info("查看购物车...");

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        lambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(lambdaQueryWrapper);

        return R.success(list);
    }
}
