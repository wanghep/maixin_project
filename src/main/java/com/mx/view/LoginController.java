package com.mx.view;

import com.alibaba.fastjson.JSONObject;
import com.mx.HttpUtil;
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
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLogRepository userLogRepository;

    @RequestMapping("register")
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {
        JSONObject body = HttpUtil.readRequestBodyToJson(request);
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            User user = userRepository.findByPhoneNumber(body.getString("phoneNumber"));
            if (user != null) {
                HttpUtil.sendFailResponse(response, "用户已存在");
                return;
            }
        }
        User entity = User.fromJson(body);
        if (currentUser != null) {
            entity.setId(currentUser.getId());
            entity.setPassword(currentUser.getPassword());
        }
        userRepository.save(entity);
        System.out.println("body = " + body);
        System.out.println(entity);
        HttpUtil.sendSuccessResponse(response);
    }

    @RequestMapping("testExample")
        public void testExample(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject body = HttpUtil.readRequestBodyToJson(request);
            String phoneNumber = body.getString("phoneNumber");
            String password = body.getString("password");
            User user = userRepository.findByPhoneNumber(phoneNumber);
            if (user.getPassword().equals(password)) {
                UserLog userLog = userLogRepository.findByUser(user);
                if (userLog == null) {
                    userLog = new UserLog();
                }
                userLog.setLoginTime(new Date());
                userLog.setUser(user);
                userLog.setSessionId(Util.getUUID());
                userLogRepository.save(userLog);
                HttpUtil.writeSuccessResponse(response, userLog.toJson(), "000000");
            } else {
                HttpUtil.sendFailResponse(response, "用户名或密码不匹配");
            }
        } catch (Exception e) {
            HttpUtil.sendFailResponse(response, "用户名或密码不匹配");
        }
    }


    @RequestMapping("changePassword")
    public void changePassword(HttpServletRequest request, HttpServletResponse response) {
        JSONObject body = HttpUtil.readRequestBodyToJson(request);
        String newPassword = body.getString("newPassword");
        userRepository.updatePassword(UserUtils.getCurrentUser().getId(), newPassword);
        HttpUtil.sendSuccessResponse(response);
    }

    @RequestMapping("")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject body = HttpUtil.readRequestBodyToJson(request);
            String phoneNumber = body.getString("phoneNumber");
            String password = body.getString("password");
            User user = userRepository.findByPhoneNumber(phoneNumber);
            if (user.getPassword().equals(password)) {
                UserLog userLog = userLogRepository.findByUser(user);
                if (userLog == null) {
                    userLog = new UserLog();
                }
                userLog.setLoginTime(new Date());
                userLog.setUser(user);
                userLog.setSessionId(Util.getUUID());
                userLogRepository.save(userLog);
                HttpUtil.writeSuccessResponse(response, userLog.toJson(), "000000");
            } else {
                HttpUtil.sendFailResponse(response, "用户名或密码不匹配");
            }
        } catch (Exception e) {
            HttpUtil.sendFailResponse(response, "用户名或密码不匹配");
        }


    }

}
