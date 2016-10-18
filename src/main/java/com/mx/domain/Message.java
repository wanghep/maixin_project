package com.mx.domain;

import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;

/**
 * Created by shenghai on 15/3/30.
 */
@Entity
@Configurable
@Table(name = "message")
public class Message extends BaseMessage {

    public void covertToLatestMessage( LatestMessage lm )
    {
        lm.setContext(this.getContext());
        lm.setDevice( this.getDevice());
        lm.setType(this.getType());
        lm.setTime(this.getTime());
    }
}
