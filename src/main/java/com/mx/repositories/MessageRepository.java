package com.mx.repositories;

import com.mx.domain.Message;
import com.mx.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by shenghai on 15/7/3.
 */
public interface MessageRepository extends CrudRepository<Message, Long> {

    //根据id获取数据源
    @Query(value = "select * from message where mac_address = ?1 and type = ?2  ", nativeQuery = true)
    public List<Message> findStormManageByMacAndType( String mac_address , int type );

    @Query(value = "select * from message where mac_address = ?1 and type = ?2  and time > DATE_SUB(now(), INTERVAL 1 HOUR )   ", nativeQuery = true)
    public List<Message> findStormManageByDeviceAndTypeInOneHour( String mac_address , int type );



}
