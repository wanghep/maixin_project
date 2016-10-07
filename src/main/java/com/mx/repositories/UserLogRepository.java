package com.mx.repositories;

import com.mx.domain.User;
import com.mx.domain.UserLog;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by shenghai on 15/7/3.
 */
public interface UserLogRepository extends CrudRepository<UserLog, Long> {
    UserLog findByUser(User user);

    UserLog findBySessionId(String sessionId);

    @Modifying
    @Query(value = "delete from user_log where user_id = :user_id", nativeQuery = true)
    void deleteByUser(@Param("user_id") Long userId);

}
