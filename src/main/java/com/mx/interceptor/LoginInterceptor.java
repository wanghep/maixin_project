package com.mx.interceptor;

import com.mx.LogUtil;
import com.mx.domain.User;
import com.mx.domain.UserUtils;
import com.mx.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by shenghai on 15/3/30.
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contextPath = request.getRequestURI();

        String sessionId = request.getHeader("sessionId");
        LogUtil.info(getClass(), "sessionId = " + sessionId  + "    contextPath = " + contextPath);
        User tyUser = loginService.checkLogIn(sessionId);

        if( tyUser == null )
        {
            LogUtil.error( this.getClass() , "loginService.checkLogIn(sessionId) = null");
        }
//        if (tyUser != null) {
            UserUtils.putCurrentUser(tyUser);
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
