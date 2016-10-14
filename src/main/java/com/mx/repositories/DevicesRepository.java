package com.mx.repositories;

import com.mx.domain.Devices;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by shenghai on 15/7/3.
 */
public interface DevicesRepository extends CrudRepository<Devices, Long> {

    //根据id获取数据源
    @Query(value = "select * from devices where mac_address=?1 ", nativeQuery = true)
    public List<Devices> findDevicesByMac( String  macAddress );

    @Query(value = "select * from devices where gardenId =? 1 ", nativeQuery = true)
    public List<Devices> findDevicesByGardenId( long gardenId );

}
