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
@Table(name = "devices")
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

        private final String DEFAULT_AVATARURL = "web\\img\\img_device_default";

    private  String name;       //设备 name

    private String avatarUrl;

    private String macAddress;        //mac address

    private int online;  //在有请求的时候更新此域 1 在线 ， 0 不在线

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne
    @JoinColumn(name = "gardenId")
    private Garden garden;

    /*
    propertyCombine:    0x01 温度
                        0x02 湿度
                        0x04 光照
                        0x08 水位
                        0x10 VOC
     */
    private  int propertyCombine; // 具有哪些属性组合

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date time;

   // private int ruleId;

    public Devices()
    {
        avatarUrl = DEFAULT_AVATARURL ;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public int getPropertyCombine() {
        return propertyCombine;
    }

    public void setPropertyCombine(int propertyCombine) {
        this.propertyCombine = propertyCombine;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }
}
