package com.mx.repositories;

import com.mx.domain.Devices;
import com.mx.domain.Garden;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by shenghai on 15/7/3.
 */
public interface GardenRepository extends CrudRepository<Garden, Long> {


}
