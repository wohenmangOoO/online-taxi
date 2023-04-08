package com.liujin.apipassenger.util;


import com.liujin.internalcommon.dto.TokenResult;

public class RedisPrefixUtils {

    private static  String verificationCodePrefix = "passenger-verification-code-";

    private static  String tokenPrefix = "token-";

    public static String passengerphoneKey(String passengerphone){
        return verificationCodePrefix+passengerphone;
    }

    public static String tokenKey(TokenResult tokenResult){
        return tokenPrefix+tokenResult.getPhone()+"-"+tokenResult.getIdentity()+"-"+tokenResult.getType();
    }
}
