package com.jerry.reggie.common;

/**
 * ClassName: CustomException
 * Package: com.jerry.reggie.common
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 11:55
 * @Version 1.0
 */

/**
 * 自定义业务异常类
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
