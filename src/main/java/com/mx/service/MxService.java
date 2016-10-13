package com.mx.service;

import com.mx.LogUtil;
import com.mx.domain.Devices;
import com.mx.domain.Message;
import com.mx.repositories.DevicesRepository;
import com.mx.repositories.MessageRepository;
import com.mx.service.mqttService.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by wanghp1 on 2016/10/11.
 */
@Service
@Transactional
public class MxService implements MessageListener {

    @Autowired
    private MQTTService mqttService;

    @Autowired
    private DevicesRepository devicesRepository;
    @Autowired
    private MessageRepository mssageRepository;

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

        if( deviceList.size() > 0 )
        {
            device = deviceList.get( 0 );
        }

        message.setDevice( device );
        message.setType( type );
        message.setContext( data1 );

        message.setTime( new Date() );

        mssageRepository.save( message );

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