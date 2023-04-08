package com.liujin.apipassenger.controller;

import com.liujin.apipassenger.service.VerificationCodeService;
import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
       String passengerphone =  verificationCodeDTO.getPassengerPhone();
        System.out.println(passengerphone);
        return verificationCodeService.getPassengerPhone(passengerphone);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return verificationCodeService.chvckVerificationCode(verificationCodeDTO);
    }

}
