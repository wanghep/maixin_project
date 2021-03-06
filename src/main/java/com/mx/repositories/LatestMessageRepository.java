package com.mx.repositories;

import com.mx.domain.LatestMessage;
import com.mx.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by shenghai on 15/7/3.
 */
public interface LatestMessageRepository extends CrudRepository<LatestMessage, Long> {

    @Query(value = "select * from latest_message where mac_address = ?1 and type = ?2 ", nativeQuery = true)
    public List<LatestMessage> findByMacAndType( String mac , String type );

    @Query(value = "select * from latest_message where mac_address = ?1 ", nativeQuery = true)
    public List<LatestMessage> findByMac(  String mac );


}
