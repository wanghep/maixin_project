package com.mx.repositories;

import com.mx.domain.Devices;
import com.mx.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by shenghai on 15/7/3.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = "update User u set u.password = :password where u.id = :id")
    void updatePassword(@Param("id") Long id, @Param("password") String password);

    @Query(value = "select * from users where open_id =?1 ", nativeQuery = true)
    public List<User> findByOpenid( String openId );

}
