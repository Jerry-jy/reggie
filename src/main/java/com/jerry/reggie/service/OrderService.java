package com.jerry.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.reggie.entity.Orders;

/**
 * ClassName: OrderService
 * Package: com.jerry.reggie.service
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-22 13:52
 * @Version 1.0
 */
public interface OrderService extends IService<Orders> {

    //用户下单
    void submit(Orders orders);
}
