package com.liujin.apipassenger.service;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.runtime.JSONFunctions;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    public String getPassengerPhone(String passengerphone ){
        System.out.println("获取验证码服务");
        System.out.println("放入到redis" );
        JSONObject jsonObject = new JSONObject();
        String code = "1111";
        jsonObject.put("code",1);
        jsonObject.put("message","sucess");
        return jsonObject.toString();
    }
}
