package com.liujin.servicepassengeruser.controller;

import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.internalcommon.request.VerificationCodeDTO;
import com.liujin.servicepassengeruser.service.PassengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassengerUserController {

    @Autowired
    private PassengerUserService passengerUserService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhoen = verificationCodeDTO.getPassengerPhone();
        return passengerUserService.loginOrRegister(passengerPhoen);

    }

}
