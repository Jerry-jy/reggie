package com.jerry.reggie.common;

/**
 * ClassName: BaseContext
 * Package: com.jerry.reggie.common
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 9:00
 * @Version 1.0
 */

/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录用户的id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal= new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
