package com.liujin.apipassenger.service;

import com.liujin.apipassenger.util.JwtUtils;
import com.liujin.apipassenger.util.RedisPrefixUtils;
import com.liujin.internalcommon.constant.CommonStatusEnum;
import com.liujin.internalcommon.constant.TokenConstants;
import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.internalcommon.dto.TokenResult;
import com.liujin.internalcommon.response.TokenResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult checkToken(String refreshToken){
        TokenResult tokenResult = parseToken(refreshToken);
        if (tokenResult == null){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        boolean token = checkRedisToken(tokenResult, refreshToken);
        if (!token){

            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String phone = tokenResult.getPhone();

        TokenResult Result = new TokenResult();
        Result.setPhone(tokenResult.getPhone());
        Result.setIdentity(tokenResult.getIdentity());
        Result.setType(TokenConstants.ASSESS_TOKEN_TYPE);
        TokenResponse tokenResponse = generatorToken(tokenResult.getPhone(), tokenResult.getIdentity());
        tokenDepositRedis(tokenResponse.getAccessToken(),Result,30);
        Result.setType(TokenConstants.REFRESH_TOKEN_TYPE);
        tokenDepositRedis(tokenResponse.getRefreshToken(),Result,31);

        return ResponseResult.success(tokenResponse);
    }

    private TokenResult parseToken(String refreshToken){
        TokenResult tokenResult = JwtUtils.parseToken(refreshToken);
        return tokenResult;
    }
    private boolean checkRedisToken(TokenResult tokenResult, String refreshToken){
        String key = RedisPrefixUtils.tokenKey(tokenResult);
        System.out.println(key.toString());
        String value = stringRedisTemplate.opsForValue().get(key);
        System.out.println(value);
        if (StringUtils.isBlank(value)){
            return false;
        }
        if (!refreshToken.trim().equals(value)){
            return false;
        }
        return true;
    }
    private TokenResponse generatorToken(String phone, String identity){
        String refreshToken = JwtUtils.generatorToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String assessToken = JwtUtils.generatorToken(phone, identity, TokenConstants.ASSESS_TOKEN_TYPE);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(assessToken);
        return tokenResponse;
    }
    private void tokenDepositRedis(String token,TokenResult tokenResult,int timeOut){
        stringRedisTemplate.opsForValue().set(RedisPrefixUtils.tokenKey(tokenResult), token,timeOut, TimeUnit.DAYS);
    }
}
