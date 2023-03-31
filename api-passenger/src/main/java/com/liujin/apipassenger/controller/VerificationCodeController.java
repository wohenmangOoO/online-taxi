package com.liujin.apipassenger.controller;

import com.liujin.apipassenger.request.VerificationCodeDTO;
import com.liujin.apipassenger.service.VerificationCodeService;
import com.liujin.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
       String passengerphone =  verificationCodeDTO.getPassengerPhone();
        return verificationCodeService.getPassengerPhone(passengerphone);
    }
}
