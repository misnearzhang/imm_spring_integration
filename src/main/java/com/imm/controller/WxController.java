package com.imm.controller;


import com.imm.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping(value = "wx")
public class WxController {
    @Value("${wx-appID}")
    private String appId;
    @Value("${wx-appsecret}")
    private String appsecret;


    /**
     * 微信自动登录回调
     */
    @RequestMapping(value = "weixinAutoLoginJump.htm")
    public String weixinAutoLoginJump(HttpServletRequest request , HttpServletResponse response) {
        try {
            String backUrl=request.getParameter("backUrl");

            String code = request.getParameter("code");
            StringBuffer tokenUrl = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token");
            tokenUrl.append("?").append("appid=");
            tokenUrl.append(appId);
            tokenUrl.append("&").append("secret=");
            tokenUrl.append(appsecret);
            tokenUrl.append("&").append("code=").append(code);
            tokenUrl.append("&").append("grant_type=authorization_code");
            String tokenRes = HttpUtils.getRequest(new URL(tokenUrl.toString()));
            return tokenRes;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    private String getOpenIdUrl(String jumpUrl) throws Exception {
        StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize");
        url.append("?").append("appId=");
        url.append(appId);
        url.append("&").append("redirect_uri=");
        url.append(URLEncoder.encode(jumpUrl,"utf-8"));
        url.append("&").append("response_type=code");
        url.append("&").append("scope=snsapi_base");
        url.append("&").append("state=").append(1);
        url.append("wechat_redirect");
        return url.toString();
    }

    /**
     * jumpUrl 这个参数是传给微信回调的
     * @param request
     * @param response
     */
    @RequestMapping(value = "weixinAutoLogin.htm")
    public void weixinAutoLogin(HttpServletRequest request , HttpServletResponse response) {
        try {
            String back = getOpenIdUrl("http://localhost:8080/wx/weixinAutoLoginJump.htm");
            response.sendRedirect(back);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
