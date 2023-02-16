package com.jerry.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ClassName: ReggieApplication
 * Package: com.jerry.reggie
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-16 13:50
 * @Version 1.0
 */
@Slf4j
@SpringBootApplication
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功...");
    }
}
