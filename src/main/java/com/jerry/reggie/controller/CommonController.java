package com.jerry.reggie.controller;

import com.jerry.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * ClassName: CommonController
 * Package: com.jerry.reggie.controller
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-19 17:19
 * @Version 1.0
 */

/**
 * 文件上传下载
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")//这里的value不要导包成了lombok，用${}动态取值
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> uploadFile(MultipartFile file) {
        //file是临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());

        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //获取原始文件名的后缀名，这里是带点的
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生成文件名，防止文件名称重复造成的文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        //创建一个目录对象
        File dir = new File(basePath);
        if (!dir.exists()) {
            //目录不存在，创建一个
            dir.mkdirs();
        }

        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            // 输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流，通过输出流将文件写回浏览器，在浏览器显示图片
            ServletOutputStream outputStream = response.getOutputStream();

            //设置输出流的类型为图片
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
               outputStream.write(bytes, 0, len);
               outputStream.flush();
            }
            // 关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
