package com.mx.view;

import com.alibaba.fastjson.JSONObject;
import com.mx.HttpUtil;
import com.mx.LogUtil;
import com.mx.Util;
import com.mx.domain.User;
import com.mx.domain.UserLog;
import com.mx.domain.UserUtils;
import com.mx.repositories.UserLogRepository;
import com.mx.repositories.UserRepository;
import com.mx.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@Controller
@Transactional
@RequestMapping("/WeiXinLogin")
public class WeiXinLoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLogRepository userLogRepository;


    @RequestMapping("weiXinLogin")
        public void WeiXinLogin(HttpServletRequest request, HttpServletResponse response) {

    }

}
