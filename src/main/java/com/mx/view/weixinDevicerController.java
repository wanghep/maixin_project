package com.mx.view;

import com.mx.Util;
import com.mx.domain.Garden;
import com.mx.domain.User;
import com.mx.repositories.GardenRepository;
import com.mx.repositories.MessageRepository;
import com.mx.repositories.UserRepository;
import com.mx.service.LoginService;
import com.mx.service.MxService;
import com.mx.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * 核心请求处理类
 * @author 简爱微萌
 * @Email zyw205@gmail.com
 *
 */
@Controller
@Transactional
    @RequestMapping("/weiXinDevice")
public class weixinDevicerController {

    @Autowired
    private MxService mxService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("myGarden")
    @ResponseBody
    public ModelAndView  myGarden(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");
        User user = loginService.checkLogIn(Util.getUUID());
        /*
        if( user == null )
        {
            //调转到登陆
            return "Login";
        }
        else
        */
        /*
        for test
        * */

        long userId = 1;
        user = userRepository.findOne(userId);

        List<Garden> GardenList = gardenRepository.findUserGarden(user.getId());


        ModelAndView modelAndView = new ModelAndView("/garden");
        modelAndView.addObject("GardenList", GardenList );
        return modelAndView;

    }

    @RequestMapping("devices")
    @ResponseBody
    public ModelAndView  devices(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");
        User user =loginService.checkLogIn( Util.getUUID());

        ModelAndView modelAndView = new ModelAndView("/manager");
        modelAndView.addObject("name", "xxx");
        return modelAndView;
    }

    @RequestMapping("deviceChart")
    @ResponseBody
    public ModelAndView  deviceChart(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");
        User user =loginService.checkLogIn( Util.getUUID());

        ModelAndView modelAndView = new ModelAndView("/device");
        modelAndView.addObject("name", "xxx");
        return modelAndView;
    }

}