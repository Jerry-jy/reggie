package com.jerry.reggie.common;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.jerry.reggie.common
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-18 16:21
 * @Version 1.0
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常捕获
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody //要返回json数据时就写
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.error(exception.getMessage());

        if (exception.getMessage().contains("Duplicate entry")){
            String[] strings = exception.getMessage().split(" ");
            String msg = strings[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
}
