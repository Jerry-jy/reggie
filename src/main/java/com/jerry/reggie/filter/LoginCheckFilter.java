package com.jerry.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.jerry.reggie.common.BaseContext;
import com.jerry.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName: LoginCheckFilter
 * Package: com.jerry.reggie.filter
 * Description:
 *
 * @Author jerry_jy
 * @Create 2023-02-16 21:50
 * @Version 1.0
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        //1、获取本次请求的URI
        String uri = request.getRequestURI(); //   backend/index.html
        log.info("拦截到请求：{}", uri);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };
        //2、判断本次请求是否需要处理
        boolean check = check(urls, uri);

        // 3、如果不需要处理，则直接放行
        if (check == true) {
            log.info("本次请求{}不需要处理" + uri);
            filterChain.doFilter(request, response);
            return;
        }

        //4、判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("empId") != null) {
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("empId"));

            Long empId = (Long) request.getSession().getAttribute("empId");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");
        // 5、如果未登录则返回未登录结果，通过输出流方式向客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param urls
     * @param uri
     * @return
     */
    public boolean check(String[] urls, String uri) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, uri);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
