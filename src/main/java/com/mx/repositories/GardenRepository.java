package com.mx.repositories;

import com.mx.domain.Devices;
import com.mx.domain.Garden;
import com.mx.domain.Message;
import com.mx.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by shenghai on 15/7/3.
 */
public interface GardenRepository extends CrudRepository<Garden, Long> {

    @Query(value = "select * from garden where user_id = ?1", nativeQuery = true)
    public List<Garden> findUserGarden( long userId );
}
