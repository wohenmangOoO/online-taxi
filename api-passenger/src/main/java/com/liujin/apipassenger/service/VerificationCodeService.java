package com.liujin.apipassenger.service;

import com.liujin.apipassenger.remote.ServicePassengerUserClient;
import com.liujin.apipassenger.remote.ServiceVerificationCodeClient;
import com.liujin.apipassenger.util.JwtUtils;
import com.liujin.apipassenger.util.RedisPrefixUtils;
import com.liujin.internalcommon.constant.CommonStatusEnum;
import com.liujin.internalcommon.constant.IdentityConstants;
import com.liujin.internalcommon.constant.TokenConstants;
import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.internalcommon.dto.TokenResult;
import com.liujin.internalcommon.request.VerificationCodeDTO;
import com.liujin.internalcommon.response.NumberCodeResponse;
import com.liujin.internalcommon.response.TokenResponse;
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
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;






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

    private void setRedis(String passengerphone, int numberCode){
        String key = RedisPrefixUtils.passengerphoneKey(passengerphone);
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
    }

    //校验验证码
    public ResponseResult chvckVerificationCode(VerificationCodeDTO verificationCodeDTO){
        String passengerphone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        String value = getValue(passengerphone);
        boolean chackCode = chackCode(value, verificationCode);
        if (!chackCode){
            return ResponseResult.fail(CommonStatusEnum.FAIL.getCode(),CommonStatusEnum.FAIL.getValue());
        }
        judgmentUserExist(passengerphone);

        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(passengerphone);
        tokenResult.setIdentity(IdentityConstants.PASSENGER_IDENTITY);
        tokenResult.setType(TokenConstants.ASSESS_TOKEN_TYPE);
        String assessToken = generatorToken(tokenResult);
        tokenDepositRedis(assessToken,tokenResult,30);

        tokenResult.setType(TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshToken = generatorToken(tokenResult);
        tokenDepositRedis(refreshToken,tokenResult,31);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(assessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
    private String generatorToken(TokenResult tokenResult){
        String token = JwtUtils.generatorToken(tokenResult.getPhone(),tokenResult.getIdentity() , tokenResult.getType());
        return token;
    }
    private void tokenDepositRedis(String token,TokenResult tokenResult,int timeOut){
        stringRedisTemplate.opsForValue().set(RedisPrefixUtils.tokenKey(tokenResult), token,timeOut,TimeUnit.DAYS);
    }
    private String getValue(String passengerphone){
        String key = RedisPrefixUtils.passengerphoneKey(passengerphone);
        return stringRedisTemplate.opsForValue().get(key);
    }
    private boolean chackCode(String value,String verificationCode){
        if (StringUtils.isBlank(value)) {
            return false;
        }
        if (!verificationCode.trim().equals(value)){
            return false;
        }

        return true;
    }
    private void judgmentUserExist(String passengerPhone){
        VerificationCodeDTO user = new VerificationCodeDTO();
        user.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(user);
    }

}
