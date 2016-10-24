package com.mx.commonStuct;


public class property {
    public final static int PROPERTY_TEMPERATURE = 0x00000001;  // 温度
    public final static int PROPERTY_HUMIDITY    =  0x00000002;  //湿度
    public final static int PROPERTY_ILLUMINATION =0x00000004; // 光照
    public final static int PROPERTY_WATER_LEVEL = 0x00000008;  //水位
    public final static int PROPERTY_VOC           = 0x00000010;  //VOC

    public final static int DEVICE_MESSAGE_TYPE_TEMPERATURE = 200;  // 温度
    public final static int DEVICE_MESSAGE_TYPE_HUMIDITY     = 201;  // 湿度
    public final static int DEVICE_MESSAGE_TYPE_ILLUMINATION = 202;  //光照
    public final static int DEVICE_MESSAGE_WATER_LEVEL      = 203;  // 水位
    public final static int DEVICE_MESSAGE_VOC                = 204;  // VOC

    public final static int DEVICE_COMMAND_START_REDUCE_TEMPERATURE  = 1 ;//            // 开始通风降温
    public final static int DEVICE_COMMAND_ST0P_REDUCE_TEMPERATURE  = 101 ;//            // 停止通风降温
    public final static int DEVICE_COMMAND_START_HUMIDITY  = 2 ;//            // 开始加湿升温
    public final static int DEVICE_COMMAND_ST0P_HUMIDITY  = 102 ;//            // 停止加湿升温
    public final static int DEVICE_COMMAND_START_ILLUMINATION  = 3 ;//            // 开始增加光照
    public final static int DEVICE_COMMAND_ST0P_ILLUMINATION  = 103 ;//            // 停止增加光照
    public final static int DEVICE_COMMAND_START_WATER  = 4 ;//            // 开始浇水
    public final static int DEVICE_COMMAND_ST0P_EATER  = 104 ;//            // 停止浇水

    public final static int DEVICE_COMMAND_MODE_AUTO  = 205 ;//           205	自动模式 +默认（温度上限40:下限5，湿度上限100%，下限10%，光照上限300,：下限40，） 实例     205 40 5 100 10 300 40 0
    public final static int DEVICE_COMMAND_MODE_MANUAL  = 206 ;//            // 206	手动模式（默认模式）



    public static int properyToCommand( int controlType )
    {
        int ret = DEVICE_COMMAND_START_REDUCE_TEMPERATURE;

        switch( controlType )
        {
            case 1: {
                ret = DEVICE_COMMAND_START_REDUCE_TEMPERATURE;
                break;
            }
            case 2: {
                ret = DEVICE_COMMAND_START_HUMIDITY;
                break;
            }
            case 3: {
                ret = DEVICE_COMMAND_START_WATER;
                break;
            }
            case 4: {
                ret = DEVICE_COMMAND_START_ILLUMINATION;
                break;
            }

        }
        return ret;
    }

    public static int properyToDeviceType( int property )
    {
        int ret = DEVICE_MESSAGE_TYPE_TEMPERATURE;

        switch( property )
        {
            case PROPERTY_TEMPERATURE: {
                ret = DEVICE_MESSAGE_TYPE_TEMPERATURE;
                break;
            }
            case PROPERTY_HUMIDITY: {
                ret = DEVICE_MESSAGE_TYPE_HUMIDITY;
                break;
            }
            case PROPERTY_ILLUMINATION: {
                ret = DEVICE_MESSAGE_TYPE_ILLUMINATION;
                break;
            }
            case PROPERTY_WATER_LEVEL: {
                ret = DEVICE_MESSAGE_WATER_LEVEL;
                break;
            }

        }
        return ret;
    }

    public static int deviceTypeToPropery( int property )
    {
        int ret = PROPERTY_TEMPERATURE;

        switch( property )
        {
            case DEVICE_MESSAGE_TYPE_TEMPERATURE: {
                ret =PROPERTY_TEMPERATURE ;
                break;
            }
            case DEVICE_MESSAGE_TYPE_HUMIDITY: {
                ret = PROPERTY_HUMIDITY;
                break;
            }
            case DEVICE_MESSAGE_TYPE_ILLUMINATION: {
                ret = PROPERTY_ILLUMINATION ;
                break;
            }
            case DEVICE_MESSAGE_WATER_LEVEL: {
                ret = PROPERTY_WATER_LEVEL;
                break;
            }

        }
        return ret;
    }
}


