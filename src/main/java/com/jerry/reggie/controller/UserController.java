package com.jerry.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jerry.reggie.common.R;
import com.jerry.reggie.entity.User;
import com.jerry.reggie.service.UserService;
import com.jerry.reggie.utils.SMSUtils;
import com.jerry.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: UserController
 * Package: com.jerry.reggie.controller
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-21 18:00
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送手机验证码短信
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //调用阿里云提供的短信服务API完成短信发送
            // 没有买短信包，就不发手机短信了
//            SMSUtils.sendMessage("reggie外卖","SMS_270890116",phone,code);

            // 需要将生成的验证码保存到 session 中
//            session.setAttribute(phone,code);

            // 将生成的验证码缓存到Redis中，并且设置有效期为5分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return R.success("手机短信验证码发送成功");
        }
        return R.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());
        //获取手机号
        String phone = map.get("phone").toString();

        // 获取验证码
        String code = map.get("code").toString();

        // 从session中获取保存的验证码
//        Object codeInSession = session.getAttribute(phone);

        // 从redis中获取缓存验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //进行验证码的比对 （页面提交过来的验证码和session中保存的验证码进行比对）
        if (codeInSession != null && codeInSession.equals(code)){
            // 如果比对成功，说明登录成功
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(lambdaQueryWrapper);

            if (user==null){
                // 判断当前手机号是否为新用户，如果是新用户就能自动完成注册
               user= new User();
               user.setPhone(phone);
               user.setStatus(1);
               userService.save(user);
            }

            session.setAttribute("user", user.getId());

            // 如果用户登录成功，删除Redis中缓存的验证码
            redisTemplate.delete(phone);

           return R.success(user);
        }
        return R.error("登录失败");
    }

    /**
     * 顾客登出功能
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    public R<String> loginout(HttpServletRequest request){
        //1、清理Session中的用户id
        request.removeAttribute("user");
        // 2、返回结果
        return R.success("退出成功");
    }
}
