package com.mx.view;

import com.alibaba.fastjson.JSONObject;
import com.mx.LogUtil;
import com.mx.Util;
import com.mx.commonStuct.property;
import com.mx.domain.*;
import com.mx.repositories.*;
import com.mx.service.LoginService;
import com.mx.service.MxService;
import com.mx.service.WeiXinService;
import com.mx.util.SignUtil;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.hibernate.internal.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private  WeiXinService weiXinService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private GardenRepository gardenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DevicesRepository devicesRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private LatestMessageRepository latestMessageRepository;

    @RequestMapping("myGarden")
    @ResponseBody
    public ModelAndView  myGarden(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");

       User user = UserUtils.getCurrentUser();
        {
            //for test
            user = new User();
            long id = 2 ;
            user.setId( id );
        }
        List<Garden> GardenList = gardenRepository.findUserGarden(user.getId());



        ModelAndView modelAndView = new ModelAndView("/garden");
        modelAndView.addObject("GardenList", GardenList );
        return modelAndView;

    }


    @RequestMapping("addGarden")
    @ResponseBody
    public ModelAndView  addGarden(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");

        ModelAndView modelAndView = new ModelAndView("/farm-create");
        return modelAndView;


    }

    @RequestMapping("devices")
    @ResponseBody
    public ModelAndView  devices(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");
        User user =loginService.checkLogIn( Util.getUUID());

        //request 中获取garden Id
        long  gardenId = Long.parseLong(request.getParameter("gardenID"));

        Garden garden = gardenRepository.findOne( gardenId );
        List<Devices> devicesList = devicesRepository.findDevicesByGardenId(gardenId);

        WxJsapiSignature wxJsapiSignature = null;
        try {
            String queryString = request.getQueryString();
            String fullPath = request.getRequestURL().toString() + "?"+queryString;   // 或者是url_buffer.toString()+queryString;
            wxJsapiSignature = weiXinService.getWxMpServiceInstance().createJsapiSignature(  fullPath );
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("/manager");

        modelAndView.addObject("appid" , wxJsapiSignature.getAppid());
        modelAndView.addObject("timestamp" , wxJsapiSignature.getTimestamp()  );
        modelAndView.addObject("nonceStr" , wxJsapiSignature.getNoncestr());
        modelAndView.addObject("signature" , wxJsapiSignature.getSignature()  );


        modelAndView.addObject("garden", garden );
        modelAndView.addObject("devicesList", devicesList );
        return modelAndView;
    }

    @RequestMapping("mode")
    @ResponseBody
    public void  mode(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException, ParseException {

        int deviceId = Integer.parseInt( request.getParameter("gardenId") );
        int mode = Integer.parseInt(request.getParameter("mode"));

        LogUtil.info(this.getClass(),deviceId);
        LogUtil.info(this.getClass(),mode);
        //update into garden  ????

        return ;
    }

    @RequestMapping("addDevice")
    @ResponseBody
    public ModelAndView  addDevice(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");

        String gardenId = request.getParameter("gardenId");
        String scanCode = request.getParameter("scanQRCodeResult");
        LogUtil.info(this.getClass(), scanCode);

        mxService.addA_DeviceByScanResult( gardenId , scanCode );
        ModelAndView modelAndView = new ModelAndView("forward:/devices");

        return modelAndView;
    }

    @RequestMapping("deviceChart")
    @ResponseBody
    public ModelAndView  deviceChart(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");
        User user =loginService.checkLogIn( Util.getUUID());

        //request 中获取garden Id
        long  deviceId = Long.parseLong(request.getParameter("deviceId"));

        Devices device = devicesRepository.findOne( deviceId );

        List<Message> messageTemperaterList = messageRepository.findStormManageByDeviceAndType( deviceId , property.DEVICE_MESSAGE_TYPE_TEMPERATURE );
        List<Message> messageHumiditList = messageRepository.findStormManageByDeviceAndType(deviceId, property.DEVICE_MESSAGE_TYPE_HUMIDITY);
        List<Message> messageIlluminationList = messageRepository.findStormManageByDeviceAndType( deviceId , property.DEVICE_MESSAGE_TYPE_ILLUMINATION );
        List<Message> messageWaterLevelList = messageRepository.findStormManageByDeviceAndType( deviceId , property.DEVICE_MESSAGE_WATER_LEVEL );

        ModelAndView modelAndView = new ModelAndView("/device");
        modelAndView.addObject("device" , device );
        modelAndView.addObject("messageTemperaterList" , messageTemperaterList );
        modelAndView.addObject("messageHumiditList" , messageHumiditList );
        modelAndView.addObject("messageIlluminationList" , messageIlluminationList );
        modelAndView.addObject("messageWaterLevelList" , messageWaterLevelList );

        return modelAndView;
    }




    @RequestMapping("/gardenAvatarUpload")
    @ResponseBody
    public String gardenAvatarUpload(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam("files") CommonsMultipartFile[] files)
            throws IllegalStateException, IOException {
        try {
            // 上传
            files[0].transferTo(new File("kkk"));
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping("deviceCommand")
    @ResponseBody
    public void  deviceCommand(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

    }

    @RequestMapping("deviceStatusIndication")
    @ResponseBody
    /* 笑脸 */
    public void  deviceStatusIndication(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        long deviceId = Long.parseLong(request.getParameter("deviceId"));
        int Type = Integer.parseInt(request.getParameter("Type"));

        LogUtil.info(this.getClass(),deviceId);
        LogUtil.info(this.getClass(),Type);
    //device
    }

    @RequestMapping("deviceRuleIndication")
    @ResponseBody
    public void  deviceRuleIndication(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {
        //device
        long deviceId = Long.parseLong(request.getParameter("deviceId"));
        int type = Integer.parseInt(request.getParameter("type"));
        int value = Integer.parseInt(request.getParameter("value"));

        LogUtil.info(this.getClass(),deviceId);
        LogUtil.info(this.getClass(),type);
        LogUtil.info(this.getClass(),value);


    }
}