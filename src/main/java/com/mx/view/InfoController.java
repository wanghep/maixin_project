package com.mx.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shenghai on 15/5/12.
 */
@Controller
@RequestMapping("/info")
public class InfoController {


    @RequestMapping("/hello")
    @ResponseBody
    public String hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");
        return "hello";
    }
}
