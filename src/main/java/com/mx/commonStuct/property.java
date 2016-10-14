package com.mx.commonStuct;


public class property {
    public final static int PROPERTY_TEMPERATURE = 0x01;  // 温度
    public final static int PROPERTY_HUMIDITY    =  0x02;  //湿度
    public final static int PROPERTY_ILLUMINATION = 0x04; // 光照
    public final static int PROPERTY_WATER_LEVEL = 0x08;  //水位

    public final static int DEVICE_MESSAGE_TYPE_TEMPERATURE = 200;  // 温度
    public final static int DEVICE_MESSAGE_TYPE_HUMIDITY     = 201;  // 湿度
    public final static int DEVICE_MESSAGE_TYPE_ILLUMINATION = 202;  //光照
    public final static int DEVICE_MESSAGE_WATER_LEVEL      = 203;  // 水位


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


