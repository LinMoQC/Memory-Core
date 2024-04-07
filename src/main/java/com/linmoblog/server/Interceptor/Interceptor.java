package com.linmoblog.server.Interceptor;

import com.linmoblog.server.Utils.JWTTokenUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {
    @Resource
    private JWTTokenUtil jwtTokenUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        System.out.println("token: " + token);
        if(token == null || !jwtTokenUtil.validateToken(token)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Forbidden");
            return false; // 阻止请求继续执行
        }
        return true;
    }
}
