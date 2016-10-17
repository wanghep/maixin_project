package com.mx.view;

import com.mx.LogUtil;
import com.mx.domain.Garden;
import com.mx.domain.User;
import com.mx.domain.UserUtils;
import com.mx.repositories.GardenRepository;
import com.mx.repositories.UserLogRepository;
import com.mx.repositories.UserRepository;
import com.mx.service.LoginService;
import com.mx.service.MxService;
import com.mx.service.WeiXinService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/WeiXinLogin")
public class WeiXinLoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private WeiXinService weiXinService;

    @Autowired
    private GardenRepository gardenRepository;

    @RequestMapping("login")
    @ResponseBody
    public ModelAndView WeiXinLogin(HttpServletRequest request, HttpServletResponse response) {

        String code;
        code = request.getParameter("code");
        WxMpUser wxMpUser = null;
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = weiXinService.getWxMpServiceInstance().oauth2getAccessToken(code);

             wxMpUser = weiXinService.getWxMpServiceInstance().oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        List<User> userList = userRepository.findByOpenid(wxMpUser.getOpenId() );
        //if( )
        User user = null;
        if( ( userList == null) || (userList.size() == 0) )
        {
            user = new User();
            user.setOpenId( wxMpUser.getOpenId() );
            userRepository.save( user );
        }
        else
        {
            user = userList.get(0);
        }
        UserUtils.putCurrentUser( user );

        List<Garden> GardenList = gardenRepository.findUserGarden(user.getId());



        ModelAndView modelAndView = new ModelAndView("/garden");
        modelAndView.addObject("GardenList", GardenList );
        return modelAndView;

    }

}
