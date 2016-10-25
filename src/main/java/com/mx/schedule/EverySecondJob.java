package com.mx.schedule;

import com.mx.LogUtil;
import org.hibernate.transform.AliasToBeanConstructorResultTransformer;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shenghai on 15/5/18.
 */
public class EverySecondJob {

    class CallBackStruct{
        public Date date ;
        public EverySecondJobCallback callback ;
        public Object para1 ;
        public Object para2;
        public Object para3;
        public CallBackStruct( Date date ,EverySecondJobCallback callback , Object para1 ,Object para2, Object para3 )
        {
            this.date = date;
            this.callback = callback;
            this.para1 = para1;
            this.para2 = para2;
            this.para3 = para3;
        }
    };

    List<CallBackStruct> callbackList = new ArrayList<CallBackStruct>();

    public void registTask( Date date ,EverySecondJobCallback callback , Object para1 ,Object para2, Object para3  )
    {

        callbackList.add( new CallBackStruct(date,callback,para1,para2,para3) );
    }


    @Transactional
    public void execute() {

        long now = new Date().getTime();
        try {
            for( int i = callbackList.size()- 1 ; i >= 0 ; i-- )
            {
                CallBackStruct cbs = callbackList.get(i);
                if( cbs.date.getTime() >= now)
                {
                    callbackList.remove(i);
                    cbs.callback.everySecondCallBackRun( cbs.para1 , cbs.para2 ,cbs.para3 );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
