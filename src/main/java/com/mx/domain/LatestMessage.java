package com.mx.domain;

import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;

/**
 * Created by shenghai on 15/3/30.
 */
@Entity
@Configurable
@Table(name = "LatestMessage")
public class  LatestMessage extends BaseMessage {

    public void covertToMessage( Message m )
    {
        m.setContext(this.getContext());
        m.setMcAddress( this.getmMcAddress());
        m.setType(this.getType());
        m.setTime(this.getTime());
    }
}
