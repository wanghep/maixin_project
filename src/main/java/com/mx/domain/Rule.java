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



}
