package com.mx.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mx.FileUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    private int validPeriod = 1 * 60 * 60 * 1000;  // ms ， 1hour
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
    private RuleRepository ruleRepository;

    @Autowired
    private  LatestMessageRepository latestMessageRepository;


    @RequestMapping("myGarden")
    @ResponseBody
    public ModelAndView  myGarden(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");

       User user = UserUtils.getCurrentUser();
        {
            //for test
            if( user == null ) {
                user = new User();
                long id = 2;
                user.setId(id);
            }
        }
        List<Garden> GardenList = gardenRepository.findUserGarden(user.getId());



        ModelAndView modelAndView = new ModelAndView("/garden");
        modelAndView.addObject("GardenList", GardenList );
        return modelAndView;

    }


    @RequestMapping("addGarden")
    @ResponseBody
    public ModelAndView  addA_Garden(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");

        ModelAndView modelAndView = new ModelAndView("/farm-create");
        return modelAndView;


    }

    @RequestMapping("addA_GardenName")
    @ResponseBody
    public void  addA_GardenName(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        response.setContentType("text/html; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");

        User user = UserUtils.getCurrentUser();
        {
            //for test
            if( user == null ) {
                user = new User();
                long id = 2;
                user.setId(id);
            }
        }
        Garden garden = new Garden();
        garden.setName( name );
        garden.setAvatarUrl("");
        garden.setRunMode( 1 ); // 自动模式
        garden.setUser(user);
        garden.setTime( new Date() );
        gardenRepository.save( garden );

        String avatarPath =  request.getContextPath() + "/views/img/" + garden.getId() + "/avatar";
        garden.setAvatarUrl( avatarPath );
        gardenRepository.save( garden );

        //copyCommonAvatarTo
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        FileUtil.CreateDirectory(rootPath + "views/img/" + garden.getId());
        FileUtil.copyFile( rootPath+"views/img/img_default_garden.png" , rootPath + "views/img/" + garden.getId()+"/avatar" );
        LogUtil.info(this.getClass(),name);
       return ;


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
        long deviceId = Long.parseLong(request.getParameter("deviceId"));
        int Type = Integer.parseInt(request.getParameter("controlType"));

        LogUtil.info(this.getClass(),deviceId);
        LogUtil.info(this.getClass(),Type);



    }

    @RequestMapping("deviceStatusReq")
    @ResponseBody
    /* 笑脸 */
    public void  deviceStatusReq(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        boolean happy = false;
        long deviceId = Long.parseLong(request.getParameter("deviceId"));
        int Type = Integer.parseInt(request.getParameter("Type"));

        LogUtil.info(this.getClass(),deviceId);
        LogUtil.info(this.getClass(),Type);


        //获得最后的数据
        List<LatestMessage> lmList = latestMessageRepository.findByDeviceIdAndType( deviceId , String.valueOf(Type) ) ;

        if(( lmList != null ) && (lmList.size()>0 ) )
        {
            LatestMessage lm = lmList.get(0);

            long diffTime =  new Date().getTime() - lm.getTime().getTime();
            if( diffTime < validPeriod )
            {// 数据是有效的

                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty(deviceId , Type );

                if( ( ruleList != null ) &&( ruleList.size() > 0 ) )
                {
                    Rule rule = ruleList.get(0);

                    int value = Integer.valueOf( lm.getContext() );

                    if(( value  <= rule.getValue1() ) && ( value >= rule.getValue2() ) )
                    {
                        happy = true;
                    }
                }
                else
                { // 暂时无规则限制
                    happy = true;
                }
            }

        }

        Map<String , Object > jasonOut = new HashMap<String , Object >();
        jasonOut.put("happy", happy );
        ajaxResponse( response , jasonOut );
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

        Rule rule = new Rule();

        int p = property.PROPERTY_TEMPERATURE;
        switch( type )
        {
            /* 温度 */
            case 1:
            {
                p = property.PROPERTY_TEMPERATURE;
                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,property.PROPERTY_TEMPERATURE );


                if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
                {
                    rule = ruleList.get(0);
                }

                rule.setValue1( value );
                break;
            }
            /* 温度 */
            case 2:
            {
                p = property.PROPERTY_TEMPERATURE;
                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,property.PROPERTY_TEMPERATURE );

                if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
                {
                    rule = ruleList.get(0);
                }

                rule.setValue2(value);
                break;
            }
            /* 温度 */
            case 3:
            {
                p = property.PROPERTY_TEMPERATURE;
                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,property.PROPERTY_TEMPERATURE );

                if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
                {
                    rule = ruleList.get(0);
                }

                rule.setValue3(value);
                break;
            }
            /* 温度 */
            case 4:
            {
                p = property.PROPERTY_TEMPERATURE;
                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,property.PROPERTY_TEMPERATURE );

                if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
                {
                    rule = ruleList.get(0);
                }

                rule.setValue4(value);
                break;
            }

            /* 湿度 */
            case 5:
            {
                p = property.PROPERTY_HUMIDITY;
                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,property.PROPERTY_HUMIDITY );

                if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
                {
                    rule = ruleList.get(0);
                }

                rule.setValue1( value);
                break;
            }

            /* 湿度 */
            case 6:
            {
                p = property.PROPERTY_HUMIDITY;
                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,property.PROPERTY_HUMIDITY );

                if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
                {
                    rule = ruleList.get(0);
                }

                rule.setValue2( value);
                break;
            }

            /* 湿度 */
            case 7:
            {
                p = property.PROPERTY_HUMIDITY;
                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,property.PROPERTY_HUMIDITY );

                if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
                {
                    rule = ruleList.get(0);
                }

                rule.setValue3(value);
                break;
            }

            /* 光照 */
            case 8:
            {
                p = property.PROPERTY_ILLUMINATION;
                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,property.PROPERTY_ILLUMINATION );

                if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
                {
                    rule = ruleList.get(0);
                }

                rule.setValue1( value);
                break;
            }

            /* 光照 */
            case 9:
            {
                p = property.PROPERTY_ILLUMINATION;
                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,property.PROPERTY_ILLUMINATION );

                if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
                {
                    rule = ruleList.get(0);
                }
                rule.setValue2(value);
                break;
            }

        }

        rule.setDeviceId( deviceId );
        rule.setProperty( p );
        ruleRepository.save( rule );

    }

    public void ajaxResponse( HttpServletResponse response , Object jasonOut )
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/javascript");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        if( jasonOut != null )
        {
            String  jsonString = JSON.toJSONString(jasonOut);//装换json

            try {
                out = response.getWriter();

                out.write( jsonString );
                out.flush();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally
            {
                if( out != null )
                {
                    out.close();
                }
            }
        }

    }
}