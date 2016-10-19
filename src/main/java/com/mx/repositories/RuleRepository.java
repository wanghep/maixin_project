package com.mx.repositories;

import com.mx.domain.Rule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by shenghai on 15/7/3.
 */
public interface RuleRepository extends CrudRepository<Rule, Long> {

    @Query(value = "select * from rule where device_id=?1 and property=?2 ", nativeQuery = true)
    public List<Rule> findRuleByDeviceIdAndProperty( long deviceId , int property );


}
