package com.liujin.apipassenger.controller;

import com.liujin.apipassenger.service.TokenService;
import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.internalcommon.dto.TokenResult;
import com.liujin.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult checkToken(@RequestBody TokenResponse TokenResponse){
        String refreshToken = TokenResponse.getRefreshToken();
        return tokenService.checkToken(refreshToken);
    }
}
