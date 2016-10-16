package com.mx.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mx.LogUtil;
import com.mx.domain.User;
import com.mx.domain.UserLog;
import com.mx.repositories.UserLogRepository;
import com.mx.service.weiXinLoginService.CommonUtil;
import com.mx.service.weiXinLoginService.SNSUserInfo;
import com.mx.service.weiXinLoginService.WeixinOauth2Token;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by shenghai on 15/3/30.
 */
@Service
@Transactional
public class WeiXinService {

    private WxMpService wxMpService;
    @Autowired
    private UserLogRepository userLogRepository;


    //TODO(user) 填写公众号开发信息
    //商站宝测试公众号 APP_ID
    //protected static final String APP_ID = "wx7f8b1923a3a9f157";
    protected static final String APP_ID = "wx86c6863828fa0a3d";
    //商站宝测试公众号 APP_SECRET
    //protected static final String APP_SECRET = "d924090936a9b9d81c83e4972f3d1577";
    protected static final String APP_SECRET = "3d44684bdd3a00c3d731902bd7c447cf";
    //商站宝测试公众号 TOKEN
    protected static final String TOKEN = "123456";
    //商站宝测试公众号 AES_KEY
    protected static final String AES_KEY = "";

    //商站宝微信支付商户号
    protected static final String PARTNER_ID = "";
    //商站宝微信支付平台商户API密钥(https://pay.weixin.qq.com/index.php/core/account/api_cert)
    protected static final String PARTNER_KEY = "";


    public WxMpService getWxMpServiceInstance()
    {
        if( wxMpService == null ) {
            wxMpService = getWxMpService();
        }
        return wxMpService;
    }

    public User checkLogIn(String sessionId) {
        UserLog log = userLogRepository.findBySessionId(sessionId);
        if (log == null) {
            return null;
        }
        return log.getUser();
    }


    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(WeiXinService.APP_ID);
        configStorage.setSecret(WeiXinService.APP_SECRET);
        configStorage.setToken(WeiXinService.TOKEN);
        configStorage.setAesKey(WeiXinService.AES_KEY);
        configStorage.setPartnerId(WeiXinService.PARTNER_ID);
        configStorage.setPartnerKey(WeiXinService.PARTNER_KEY);
        return configStorage;
    }


    private WxMpService getWxMpService() {
        wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());

        return wxMpService;
    }

    /**
     * 获取接口访问凭证
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    //http://mp.weixin.qq.com/wiki/15/54ce45d8d30b6bf6758f68d2e95bc627.html
    public String getAccess_token( ) {
        //凭证获取(GET)
        String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        String requestUrl = token_url.replace("APPID", APP_ID).replace("APPSECRET", APP_SECRET);
        // 发起GET请求获取凭证
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        String access_token = null;
        if (null != jsonObject) {
            try {
                access_token = jsonObject.getString("access_token");
            } catch (JSONException e) {
                // 获取token失败
                LogUtil.error( this.getClass() , "获取token失败 errcode= "+ jsonObject.getInteger("errcode") + "errmsg =  " +  jsonObject.getString("errmsg") );
            }
        }
        return access_token;
    }


    public static String getAppId() {
        return APP_ID;
    }

    public static String getAppSecret() {
        return APP_SECRET;
    }

    public static String getToken() {
        return TOKEN;
    }

    public static String getAesKey() {
        return AES_KEY;
    }

    public static String getPartnerId() {
        return PARTNER_ID;
    }

    public static String getPartnerKey() {
        return PARTNER_KEY;
    }
}
