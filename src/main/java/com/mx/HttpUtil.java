package com.mx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by shenghai on 15/5/4.
 */
public class HttpUtil {
    public static JSONObject readRequestToJson(HttpServletRequest request) {
        return (JSONObject) JSON.parse(readRequest(request));
    }

    public static JSONObject readRequestBodyToJson(HttpServletRequest request) {
        return ((JSONObject) JSON.parse(readRequest(request))).getJSONObject("BODY");
    }

    public static String readRequest(HttpServletRequest request) {
        String json;
        try {
            json = IOUtils.toString(request.getInputStream(), "utf-8");
            LogUtil.info(HttpUtil.class, "request = " + json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static void writeResponse(HttpServletResponse response, JSONObject result) {
        writeResponse(response, result.toJSONString());
    }

    public static void writeResponse(HttpServletResponse response, JSONObject body,String code, String message) {
        JSONObject result = new JSONObject();
        JSONObject sysHead = new JSONObject();
        sysHead.put("RSP_CODE", code);
        sysHead.put("RSP_MSG", message);
        result.put("SYSHEAD", sysHead);
        result.put("BODY", body);
        writeResponse(response, result);
    }


    public static void writeSuccessResponse(HttpServletResponse response, JSON body, String message) {
        JSONObject result = new JSONObject();
        JSONObject sysHead = new JSONObject();
        sysHead.put("RSP_CODE", "000000");
        sysHead.put("RSP_MSG", message);
        result.put("SYSHEAD", sysHead);
        result.put("BODY", body);
        writeResponse(response, result);
    }

    public static void sendSuccessResponse(HttpServletResponse response) {
        JSONObject result = new JSONObject();
        JSONObject sysHead = new JSONObject();
        sysHead.put("RSP_CODE", "000000");
        sysHead.put("RSP_MSG", "000000");
        result.put("SYSHEAD", sysHead);
        result.put("BODY", new JSONObject());
        writeResponse(response, result);
    }

    public static void sendFailResponse(HttpServletResponse response, String message) {
        JSONObject result = new JSONObject();
        JSONObject sysHead = new JSONObject();
        sysHead.put("RSP_CODE", "000001");
        sysHead.put("RSP_MSG", message);
        result.put("SYSHEAD", sysHead);
        result.put("BODY", new JSONObject());
        writeResponse(response, result);
    }


    public static void writeResponse(HttpServletResponse response, String result) {
        LogUtil.info(HttpUtil.class, "response = " + result);
        response.setContentType("application/json; encoding=utf-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
