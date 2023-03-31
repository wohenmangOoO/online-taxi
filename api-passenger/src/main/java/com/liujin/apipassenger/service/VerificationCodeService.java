package com.liujin.apipassenger.service;

import com.liujin.apipassenger.remote.ServiceVerificationCodeClient;
import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private String verificationCodePrefix = "passenger-verification-code-";

    public ResponseResult getPassengerPhone(String passengerphone ){
        int numbercode = numberCoderesponse();
        setRedis(passengerphone,numbercode);
        return ResponseResult.success();
    }
    private int numberCoderesponse(){
        ResponseResult<NumberCodeResponse> numberCoderesponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCoderesponse.getData().getNumbercode();
        return numberCode;
    }
    private void setRedis(String passengerphone, int numberCode){
        String key = verificationCodePrefix+passengerphone;
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
    }
}
