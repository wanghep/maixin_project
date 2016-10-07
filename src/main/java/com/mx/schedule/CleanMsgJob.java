package com.mx.schedule;

import com.mx.LogUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by shenghai on 15/5/18.
 */
public class CleanMsgJob {

    protected EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }


    @Transactional
    public void execute() {
        LogUtil.info(getClass(), "CleanMsgJob start");
        try {
            LogUtil.info(getClass(), "cleanMsgJob finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
