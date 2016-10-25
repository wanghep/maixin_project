package com.mx.service;

import com.alibaba.fastjson.JSONObject;
import com.mx.FileUtil;
import com.mx.LogUtil;
import com.mx.commonStuct.property;
import com.mx.domain.*;
import com.mx.repositories.*;
import com.mx.service.mqttService.MessageListener;
import freemarker.ext.beans.HashAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghp1 on 2016/10/11.
 */
@Service
@Transactional
public class MxService implements MessageListener {

    @Autowired
    private MQTTService mqttService;

    @Autowired
    private GardenRepository gardenRepository;
    @Autowired
    private DevicesRepository devicesRepository;
    @Autowired
    private MessageRepository mssageRepository;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private LatestMessageRepository latestMessageRepository;

    //???
    @Autowired
    public MxService( MQTTService mqttService )
    {
        mqttService.registerMessageListener( this );
    }

    String COMMAND_START_DESCREASE_TEMPERATURE =   "1"   ;       // 开始通风降温
    String COMMAND_STOP_DESCREASE_TEMPERATURE  =  "101"  ;      // 停止通风降温
    String COMMAND_START_INCREASE_TEMPERATURE  =  "2"    ;      //  开始加湿升温
    String COMMAND_STOP_INCREASE_TEMPERATURE   = "102"   ;     // 停止加湿升温;
    String COMMAND_START_INCREASE_ILLUMINATION =   "3"   ;       //  开始增加光照
    String COMMAND_STOP_INCREASE_ILLUMINATION   = "103"   ;     // 停止增加光照
    String COMMAND_START_WATERING                 = "4"        ; // 开始浇水
    String COMMAND_STOP_WATERING                  = "104"       ;// 停止浇水
    String COMMAND_REPORT_TEMPERATURE_VALUE   = "200"  ;     //上报温度值
    String COMMAND_REPORT_HUMIDITY_VALUE      = "201"  ;     //上报湿度值
    String COMMAND_REPORT_ILLUMINATION_VALUE  = "202"  ;     //上报光照值
    String COMMAND_REPORT_WATER_LEVEL_VALUE   = "203"  ;     //上报水位值




    public void sendCommandToDevice( String macAddress , String type , String value1 , String Value2 )
    {
        if(mqttService != null){
            mqttService.sendMessage(macAddress,type , value1 );
        }
        else
        {
            LogUtil.error( MxService.class , "mqttService is null ,fatal error" );
        }
    }

    @Override
    public void receiveMessage( String deviceID, String type, String data1 ,String data2  ) {


        Message message = new Message();

        List<Devices> deviceList = devicesRepository.findDevicesByMac( deviceID );

        Devices  device = null;

        if( deviceList != null && ( deviceList.size() > 0 ) )
        {
            device = deviceList.get( 0 );
            message.setDevice( device );
            message.setType( type );
            message.setContext( data1 );

            message.setTime( new Date() );

            mssageRepository.save( message );


            List<LatestMessage> lmList = latestMessageRepository.findByDeviceIdAndType(device.getId() , type );


            //更新到最新的消息库中
            LatestMessage lm = null;
            if( lmList != null && lmList.size() > 0 )
            {
                lm = lmList.get(0);
            }
            else
            {
                lm = new LatestMessage();

            }
            message.covertToLatestMessage( lm );
            latestMessageRepository.save(lm);


        }



    }

    //"macId:"123456789","type"="3";
    public long  addA_DeviceByScanResult( HttpServletRequest request, String gardenId , String scanResult )
    {
        JSONObject json= JSONObject.parseObject(scanResult);
        String errMsg = json.getString("errMsg");
        String resultStr = json.getString("resultStr");
        long deviceId = -1;

        if( errMsg.equals( "scanQRCode:ok"))
        { // 得到正确结果
            JSONObject jsonDeviceInfo = JSONObject.parseObject( resultStr );
            String mac = jsonDeviceInfo.getString("mac");
            String type = jsonDeviceInfo.getString("type");
            if( ( mac != null) && (type!= null)
                    && ( mac != "") && (type!= ""))
            {
                Devices devices = new Devices();

                devices.setName("新增设备");
                devices.setGarden(gardenRepository.findOne(Long.parseLong(gardenId)));
                User user = UserUtils.getCurrentUser();
                devices.setUser(user);
                devices.setMacAddress(mac);
                devices.setPropertyCombine(Integer.parseInt(type));
                devices.setAvatarUrl("");
                devices.setTime(new Date());

                devicesRepository.save(devices);
                String avatarPath =  request.getContextPath() + "/views/img/" + gardenId + "/device" + devices.getId() +"_avatar";

                devices.setAvatarUrl(avatarPath);
                devicesRepository.save(devices);

                deviceId = devices.getId();

                //copyCommonAvatarTo
                String rootPath = request.getSession().getServletContext().getRealPath("/");
                String targetDeviceUrl = rootPath + "/views/img/" + gardenId + "/device" + devices.getId() +"_avatar";
                try {
                    FileUtil.copyFile( rootPath+"views/img/img_device_default.png" ,targetDeviceUrl  );
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


        return deviceId;
    }

    public void generateRuleAccording( long deviceId )
    {
         Devices device = null;

        device = devicesRepository.findOne( deviceId );
        int combine = device.getPropertyCombine();

        int p = property.PROPERTY_TEMPERATURE;


        if( ( combine & property.PROPERTY_TEMPERATURE) != 0 )
        { //温度
            p = property.PROPERTY_TEMPERATURE;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );


            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {

            }
            else
            {
                Rule rule = new Rule();
                rule.setDeviceId( deviceId );
                rule.setProperty( p );
                rule.setValue1( 40 );
                rule.setValue1( 5 );
                ruleRepository.save( rule );
            }


        }
        if( ( combine & property.PROPERTY_HUMIDITY) != 0 )
        { //湿度
            p = property.PROPERTY_HUMIDITY;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );


            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {

            }
            else
            {
                Rule rule = new Rule();
                rule.setDeviceId( deviceId );
                rule.setProperty( p );
                rule.setValue1( 100 );//湿度上限100%，下限10%，湿度上限100%，下限10%，
                rule.setValue1( 10 );
                ruleRepository.save( rule );
            }
        }
        if( ( combine & property.PROPERTY_ILLUMINATION) != 0 )
        { //光照
            p = property.PROPERTY_ILLUMINATION;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );


            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {

            }
            else
            {
                Rule rule = new Rule();
                rule.setDeviceId( deviceId );
                rule.setProperty( p );
                rule.setValue1( 300 );//光照上限300,：下限40
                rule.setValue1( 40 );
                ruleRepository.save( rule );
            }
        }
        if( ( combine & property.PROPERTY_WATER_LEVEL) != 0 )
        { //水位
            p = property.PROPERTY_WATER_LEVEL;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );


            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {

            }
            else
            {
                Rule rule = new Rule();
                rule.setDeviceId( deviceId );
                rule.setProperty( p );
                rule.setValue1( 20 );//
                rule.setValue1( 10 );
                ruleRepository.save( rule );
            }
        }
        if( ( combine & property.PROPERTY_VOC) != 0 )
        { //VOC
            p = property.PROPERTY_VOC;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );


            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {

            }
            else
            {
                Rule rule = new Rule();
                rule.setDeviceId( deviceId );
                rule.setProperty( p );
                rule.setValue1( 30 );//
                rule.setValue1( 10 );
                ruleRepository.save( rule );
            }
        }
    }

    /*
    ruleSet.put( "temperatureHigh" , rule.getValue1() );
    ruleSet.put( "temperatureLow"  , rule.getValue2() );
    ruleSet.put("humidityHigh", rule.getValue1());
    ruleSet.put("humidityLow", rule.getValue2());
    ruleSet.put("illuminationHigh", rule.getValue1());
    ruleSet.put("illuminationLow", rule.getValue2());
    ruleSet.put("waterLevelHigh", rule.getValue1());
    ruleSet.put("waterLevelLow", rule.getValue2());
    ruleSet.put("vocHigh", rule.getValue1());
    ruleSet.put("vocLow", rule.getValue2());

     */
    public Map getRuleById( long deviceId )
    {
        Map ret = null ;

        ret = getRuleAccordingDeviceId( deviceId );
        if( ret == null )
        {
            this.generateRuleAccording( deviceId );
            ret = getRuleAccordingDeviceId( deviceId );
        }

        return ret;
    }
    private Map getRuleAccordingDeviceId( long deviceId )
    {
        Devices device = null;

        Map<String , Integer > ruleSet = new HashMap<>();
        device = devicesRepository.findOne( deviceId );
        int combine = device.getPropertyCombine();

        int p = property.PROPERTY_TEMPERATURE;


        if( ( combine & property.PROPERTY_TEMPERATURE) != 0 )
        { //温度
            p = property.PROPERTY_TEMPERATURE;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );


            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {
                Rule rule = ruleList.get(0);
                ruleSet.put( "temperatureHigh" , rule.getValue1() );
                ruleSet.put( "temperatureLow"  , rule.getValue2() );
            }
            else
            {
                return null;
            }

        }
        if( ( combine & property.PROPERTY_HUMIDITY) != 0 )
        { //湿度
            p = property.PROPERTY_HUMIDITY;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );

            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {
                Rule rule = ruleList.get(0);
                ruleSet.put("humidityHigh", rule.getValue1());
                ruleSet.put("humidityLow", rule.getValue2());
            }
            else
            {
                return null;
            }
        }
        if( ( combine & property.PROPERTY_ILLUMINATION) != 0 )
        { //光照
            p = property.PROPERTY_ILLUMINATION;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );

            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {
                Rule rule = ruleList.get(0);
                ruleSet.put("illuminationHigh", rule.getValue1());
                ruleSet.put("illuminationLow", rule.getValue2());
            }
            else
            {
                return null;
            }
        }
        if( ( combine & property.PROPERTY_WATER_LEVEL) != 0 )
        { //水位
            p = property.PROPERTY_WATER_LEVEL;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );

            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {
                Rule rule = ruleList.get(0);
                ruleSet.put("waterLevelHigh", rule.getValue1());
                ruleSet.put("waterLevelLow", rule.getValue2());
            }
            else
            {
                return null;
            }
        }
        if( ( combine & property.PROPERTY_VOC) != 0 )
        { //VOC
            p = property.PROPERTY_VOC;
            List<Rule> ruleList = ruleRepository.findRuleByDeviceIdAndProperty( deviceId ,p );

            if( ( ruleList!=null ) && ( ruleList.size() > 0 ))
            {
                Rule rule = ruleList.get(0);
                ruleSet.put("vocHigh", rule.getValue1());
                ruleSet.put("vocLow", rule.getValue2());
            }
            else
            {
                return null;
            }
        }

        return ruleSet;
    }
}


/*

  设备向后台（emqtt）发送消息的 Subscription 为： device/message ,QOS= 0

消息格式刷如下（注意和之前的框架有修改，之前采用的是字节定义,改为字符串形式）：
//地址   空格  消息类型  空格  数据值1    数据值2（可选）
111111        1                    21           0

以上是为了方便看，其中空格的地方都是一个空格不是多个空格例如如下（111111 1 21 0 ），如果没有数据2则可以没有此项

   后台server 发出的格式为 ，具体的 Subscription 为各自的mac值形式为 server/mac address（字符串）:具体实例为  server/123456781234   123456781234为16进制形式的字符串

消息类型  空格  数据
1                    21

无论是设备发往server还是server发往设备，消息类型同意定义：目前定义如下
type        含义
1             开始通风降温
101         停止通风降温

2             开始加湿升温
102         停止加湿升温

3             开始增加光照
103         停止增加光照

4             开始浇水
104         停止浇水

200        上报温度值
201        上报湿度值
202        上报光照值
203        上报水位值
 */