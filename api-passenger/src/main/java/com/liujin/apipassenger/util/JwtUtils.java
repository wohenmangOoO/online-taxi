package com.liujin.apipassenger.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liujin.internalcommon.dto.TokenResult;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET = "liujin";

    private static final String JWT_TOKEN_PHONE ="phone";
    private static final String JWT_TOKEN_IDENTITY ="identity";
    private static final String JWT_TOKEN_TYPE ="type";
    private static final String JWT_TOKEN_TIME ="tokenTime";

    public static String generatorToken(String phone, String identity, String type){
        Map<String,String> map = new HashMap<>();
        map.put(JWT_TOKEN_PHONE,phone);
        map.put(JWT_TOKEN_IDENTITY,identity);
        map.put(JWT_TOKEN_TYPE,type);
        map.put(JWT_TOKEN_TIME,Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v) -> {
            builder.withClaim(k,v);
        } );
        String sign = builder.sign(Algorithm.HMAC256(SECRET));
        return sign;

    }

    public  static TokenResult parseToken(String token){

        TokenResult tokenResult = null;
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            String phone = verify.getClaim(JWT_TOKEN_PHONE).asString();
            String identity = verify.getClaim(JWT_TOKEN_IDENTITY).asString();
            String type = verify.getClaim(JWT_TOKEN_TYPE).asString();
            TokenResult result = new TokenResult();
            result.setPhone(phone);
            result.setIdentity(identity);
            result.setType(type);
            tokenResult = result;
        }catch (Exception s){

        }

        return tokenResult;
    }


    public static void main(String[] args) {
//        String s = JwtUtils.generatorToken("1502479553",);
//        System.out.println(parseToken(s));
    }
}
