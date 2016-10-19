package com.mx.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Created by shenghai on 15/3/30.
 */
@Entity
@Configurable
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private long deviceId;

    private int property; // 0x01 02 04 08 ...

    /* value 值解释
    如果 property = PROPERTY_TEMPERATURE ： 温度
        value1 ：自动模式上限  value2 ：自动模式下限  value3：手动模式通风时间 value4:手动模式加热时间

    如果 property = PROPERTY_HUMIDITY ： 温度
        value1 ：自动模式上限  value2 ：自动模式下限  value3：手动模式浇水时间

    如果 property = PROPERTY_ILLUMINATION ： 温度
        value1 ：自动模式上限  value2 ：自动模式下限
     */

    private int value1;     //-1 invalid

    private int value2;     //-1 invalid

    private int value3;     //-1 invalid

    private int value4;     //-1 invalid

    private int value5;

    public Rule()
    {
        value1 = -1;
        value2 = -1;
        value3 = -1;
        value4 = -1;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int propery) {
        this.property = propery;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public int getValue3() {
        return value3;
    }

    public void setValue3(int value3) {
        this.value3 = value3;
    }

    public int getValue4() {
        return value4;
    }

    public void setValue4(int value4) {
        this.value4 = value4;
    }

    public int getValue5() {
        return value5;
    }

    public void setValue5(int value5) {
        this.value5 = value5;
    }
}
