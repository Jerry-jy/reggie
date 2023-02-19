package com.reggie.test;

import org.junit.jupiter.api.Test;

/**
 * ClassName: UploadFileTest
 * Package: com.reggie.test
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 18:00
 * @Version 1.0
 */
public class UploadFileTest {
    @Test
    public void test1(){
        String fileName = "fhshsagjkdhsajkn.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("suffix = " + suffix);
    }
}
