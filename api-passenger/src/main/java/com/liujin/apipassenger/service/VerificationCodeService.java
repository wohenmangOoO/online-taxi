package com.liujin.apipassenger.service;

import com.liujin.apipassenger.remote.ServiceVerificationCodeClient;
import com.liujin.apipassenger.request.VerificationCodeDTO;
import com.liujin.internalcommon.constant.CommonStatusEnum;
import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.internalcommon.response.NumberCodeResponse;
import com.liujin.internalcommon.response.TokenResoinse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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

    //生成验证码
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
    private String passengerphoneKey(String passengerphone){
        return verificationCodePrefix+passengerphone;
    }
    private void setRedis(String passengerphone, int numberCode){
        stringRedisTemplate.opsForValue().set(passengerphoneKey(passengerphone),numberCode+"",2, TimeUnit.MINUTES);
    }


    public ResponseResult chvckVerificationCode(VerificationCodeDTO verificationCodeDTO){
        String passengerphone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        String value = getValue(passengerphone);
        return chackCode(value,verificationCode);
    }
    private String getValue(String passengerphone){
        String key = passengerphoneKey(passengerphone);
        return stringRedisTemplate.opsForValue().get(key);
    }
    private ResponseResult chackCode(String value,String verificationCode){
        if (StringUtils.isBlank(value)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (verificationCode.trim().equals(value)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        TokenResoinse token = new TokenResoinse();
        token.setToken("token str");
        return ResponseResult.success(CommonStatusEnum.VERIFICATION_CODE_SUCCESS.getCode(),CommonStatusEnum.VERIFICATION_CODE_SUCCESS.getValue(), token);
    }
}
