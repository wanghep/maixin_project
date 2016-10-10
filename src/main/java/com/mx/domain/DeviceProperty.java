package com.mx.domain;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shenghai on 15/3/30.
 */
@Entity
@Configurable
@Table(name = "deviceProperty")
public class DeviceProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    private  String properrtyGroup;       //每种熟悉对应的传感器组合，用字符串的像是标识

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProperrtyGroup() {
        return properrtyGroup;
    }

    public void setProperrtyGroup(String properrtyGroup) {
        this.properrtyGroup = properrtyGroup;
    }
}
