package com.mx.repositories;

import com.mx.domain.DeviceProperty;
import com.mx.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by shenghai on 15/7/3.
 */
public interface DevicePropertyRepository extends CrudRepository<DeviceProperty, Long> {


}
