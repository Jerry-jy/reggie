package com.jerry.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jerry.reggie.common.R;
import com.jerry.reggie.entity.Employee;
import com.jerry.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * ClassName: EmployeeController
 * Package: com.jerry.reggie.controller
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-16 14:52
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工后台登录功能
     * @param employee
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
        //@RequestBody用来接收前端传递给后端的`json`字符串中的数据的(请求体中的数据的)，所以前端只能发送POST请求

        //1、将页面提交的密码password进行md5加密处理
        String pwd = employee.getPassword();
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());

        // 2、根据页面提交的用户名username查询数据库]
        /**
         * 我自己写的是
         *         QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
         *         employeeService.getOne(queryWrapper.select(employee.getName()));
         * 查询出来的是null值
         */
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        //方法引用的语法格式（语法糖）
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);

        // 3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");
        }
        //4、密码比对，如果不一致则返回登录失败结果
        if (!pwd.equals(emp.getPassword())) {
            return R.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() != 1) {
            return R.error("员工账号已禁用");
        }

        // 6、登录成功，将员工id存入Session并返回登录成功结果
        Long empId = emp.getId();
        request.getSession().setAttribute("empId", empId);
        return R.success(emp);
    }

    /**
     * 员工后台登出功能
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //1、清理Session中的用户id
        request.getSession().removeAttribute("empId");
        // 2、返回结果
        return R.success("登出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request){
        log.info("新增员工，员工信息：{}",employee.toString());

        //设置员工的初始密码，需要进行MD5 加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));


        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //获得当前登录对象的id
        Long empId = (Long) request.getSession().getAttribute("empId");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("添加员工成功");
    }
}
