package com.mx.service;

import com.mx.LogUtil;
import com.mx.domain.User;
import com.mx.domain.UserLog;
import com.mx.repositories.UserLogRepository;
import com.mx.service.weiXinLoginService.SNSUserInfo;
import com.mx.service.weiXinLoginService.WeixinOauth2Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by shenghai on 15/3/30.
 */
@Service
@Transactional
public class WeiXinLoginService {

    @Autowired
    private UserLogRepository userLogRepository;

    public User checkLogIn(String sessionId) {
        UserLog log = userLogRepository.findBySessionId(sessionId);
        if (log == null) {
            return null;
        }
        return log.getUser();
    }

}
