package com.liujin.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.liujin.apipassenger.util.JwtUtils;
import com.liujin.apipassenger.util.RedisPrefixUtils;
import com.liujin.internalcommon.constant.TokenConstants;
import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.internalcommon.dto.TokenResult;
import com.sun.javafx.geom.transform.SingularMatrixException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("authorization");
        boolean result = true;
        String resultString = "";

        TokenResult tokenResult = JwtUtils.parseToken(authorization);
        if (tokenResult == null){
            result = false;
            resultString = "token invalid";
        }else {
            tokenResult.setType(TokenConstants.ASSESS_TOKEN_TYPE);
            String key = RedisPrefixUtils.tokenKey(tokenResult);
            String token = stringRedisTemplate.opsForValue().get(key);

            boolean chacktoken = chacktoken(token, authorization);
            result = chacktoken;
        }


        if (!result){
            resultString = "accesstoken invalid";
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)));
        }

        return result;
    }

    private boolean chacktoken(String value,String token){
        if (StringUtils.isBlank(value) || !token.trim().equals(value)) {
            return false;
        }
        return true;
    }
}
