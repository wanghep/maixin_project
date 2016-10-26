package com.mx.view;

import com.alibaba.fastjson.JSON;
import com.mx.FileUtil;
import com.mx.LogUtil;
import com.mx.Util;
import com.mx.commonStuct.property;
import com.mx.domain.*;
import com.mx.repositories.*;
import com.mx.schedule.EverySecondJob;
import com.mx.schedule.EverySecondJobCallback;
import com.mx.service.LoginService;
import com.mx.service.MxService;
import com.mx.service.WeiXinService;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
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
public class weixinDevicerController implements EverySecondJobCallback {

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

    @Autowired
    private EverySecondJob everySecondJob;


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

        String avatarPath =  request.getContextPath() + "/views/img/" + garden.getId() + "/garden_avatar";
        garden.setAvatarUrl( avatarPath );
        gardenRepository.save( garden );

        //copyCommonAvatarTo
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        FileUtil.CreateDirectory(rootPath + "views/img/" + garden.getId());
        FileUtil.copyFile( rootPath+"views/img/img_default_garden.png" , rootPath + "views/img/" + garden.getId()+"/garden_avatar" );
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

        /* 更新设备是否在线标识 */
        long now = new Date().getTime();
        for( int i = 0 ; i < devicesList.size() ; i++ )
        {
            List<LatestMessage> lmList =  latestMessageRepository.findByDeviceId(devicesList.get(i).getId());
            if( ( lmList != null) && (lmList.size() >0 ) )
            {
                Date lastTime = lmList.get(0).getTime();

                if( now - lastTime.getTime() > 5 * 60 * 1000 )
                {
                    devicesList.get(i).setOnline( 0 );
                }
                else
                {
                    devicesList.get(i).setOnline( 1 );
                }
            }
        }


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

        long deviceId = Long.parseLong(request.getParameter("gardenId"));
        int mode = Integer.parseInt(request.getParameter("mode"));

        LogUtil.info(this.getClass(),deviceId);
        LogUtil.info(this.getClass(),mode);

        Garden garden = gardenRepository.findOne(deviceId);

        if( garden != null )
        {
            garden.setRunMode( mode );
            gardenRepository.save(garden);
        }

        List<Devices> deviceList = devicesRepository.findDevicesByGardenId( garden.getId() );
        for( int i = 0 ; i < deviceList.size() ; i++ ) {
            if( mode == 1 ) { //自动模式
                Devices device = deviceList.get(i);

                Map<String,Integer> ruleSet = mxService.getRuleById( device.getId() );

                String rule ="";

                if(ruleSet.get( "temperatureHigh") != null  )        ;
                {
                    rule += " " +  ruleSet.get( "temperatureHigh");
                }
                if(ruleSet.get( "temperatureLow") != null  )        ;
                {
                    rule += " " +  ruleSet.get( "temperatureLow");
                }

                if(ruleSet.get( "humidityHigh") != null  )        ;
                {
                    rule += " " +  ruleSet.get( "humidityHigh");
                }
                if(ruleSet.get( "humidityLow") != null  )        ;
                {
                    rule += " " +  ruleSet.get( "humidityLow");
                }

                if(ruleSet.get( "illuminationHigh") != null  )        ;
                {
                    rule += " " +  ruleSet.get( "illuminationHigh");
                }
                if(ruleSet.get( "illuminationLow") != null  )        ;
                {
                    rule += " " +  ruleSet.get( "illuminationLow");
                }



                mxService.sendCommandToDevice(deviceList.get(i).getMacAddress(), "205", rule, "0");
            }
            else
            { //手动模式
                mxService.sendCommandToDevice(deviceList.get(i).getMacAddress(), "206", "0", "0");
            }
        }

        Map<String , Object > jasonOut = new HashMap<String , Object >();
        jasonOut.put("mode", mode );
        ajaxResponse( response , jasonOut );

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

        long newDeviceId = mxService.addA_DeviceByScanResult( request , gardenId , scanCode );

        if( newDeviceId != -1 )
        {
            mxService.generateRuleAccording( newDeviceId ); // 更新 rule
        }
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

        List<Message> messageTemperaterList = messageRepository.findStormManageByDeviceAndTypeInOneHour(deviceId, property.DEVICE_MESSAGE_TYPE_TEMPERATURE);
        List<Message> messageHumiditList = messageRepository.findStormManageByDeviceAndTypeInOneHour(deviceId, property.DEVICE_MESSAGE_TYPE_HUMIDITY);
        List<Message> messageIlluminationList = messageRepository.findStormManageByDeviceAndTypeInOneHour(deviceId, property.DEVICE_MESSAGE_TYPE_ILLUMINATION);
        List<Message> messageWaterLevelList = messageRepository.findStormManageByDeviceAndTypeInOneHour(deviceId, property.DEVICE_MESSAGE_WATER_LEVEL);
        List<Message> messageVocList = messageRepository.findStormManageByDeviceAndTypeInOneHour( deviceId , property.DEVICE_MESSAGE_VOC );

        Map ruleSet = mxService.getRuleById( deviceId );

        ModelAndView modelAndView = new ModelAndView("/device");
        modelAndView.addObject("device" , device );
        modelAndView.addObject("messageTemperaterList" , messageTemperaterList );
        modelAndView.addObject("messageHumiditList" , messageHumiditList );
        modelAndView.addObject("messageIlluminationList" , messageIlluminationList );
        modelAndView.addObject("messageWaterLevelList" , messageWaterLevelList );
        modelAndView.addObject("messageVocList" , messageVocList );
        modelAndView.addObject("ruleSet",ruleSet);

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
        int type = Integer.parseInt(request.getParameter("controlType"));

        LogUtil.info(this.getClass(),deviceId);
        LogUtil.info(this.getClass(),type);

        int startCommand = property.properyToStartCommand(type);
        int stopCommand = property.properyToStopCommand( type );
        Devices device = devicesRepository.findOne( deviceId );
        if( device != null )
        {
            mxService.sendCommandToDevice( device.getMacAddress() ,String.valueOf(startCommand),"0","0" );

            //暂时固定设置为10秒钟后执行
            everySecondJob.registTask( new Date(System.currentTimeMillis() + 1000000) ,this, device, stopCommand, null );
        }

    }

    @RequestMapping("deviceStatusReq")
    @ResponseBody
    /* 笑脸 */
    public void  deviceStatusReq(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException, IllegalAccessException {

        boolean happy = false;
        long deviceId = Long.parseLong(request.getParameter("deviceId"));
        int type = Integer.parseInt(request.getParameter("Type"));

        LogUtil.info(this.getClass(),deviceId);
        LogUtil.info(this.getClass(),type);


        int reportType = property.properyToDeviceType( type );
        //获得最后的数据
        List<LatestMessage> lmList = latestMessageRepository.findByDeviceIdAndType( deviceId , String.valueOf(reportType) ) ;

        if(( lmList != null ) && (lmList.size()>0 ) )
        {
            LatestMessage lm = lmList.get(0);

            long diffTime =  new Date().getTime() - lm.getTime().getTime();
            if( diffTime < validPeriod )
            {// 数据是有效的

                List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty(deviceId , type );

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

    @Override
    public void everySecondCallBackRun(Object para1, Object para2, Object para3) {
        int stopCommand = (int)para2;
        Devices device = (Devices)para1;
        if( device != null ) {
            LogUtil.info(getClass(), "everySecondCallBackRun : sendCommandToDevice  " + device.getMacAddress() + "  " +String.valueOf(stopCommand) );
            mxService.sendCommandToDevice(device.getMacAddress(), String.valueOf(stopCommand), "0", "0");
        }
    }
}