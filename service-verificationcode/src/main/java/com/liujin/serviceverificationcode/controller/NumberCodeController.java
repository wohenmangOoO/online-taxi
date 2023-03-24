package com.liujin.serviceverificationcode.controller;

import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size){
        //生成验证码
        double data =(Math.random() * 9 + 1) * (Math.pow(10, size - 1));
        int resultInt = (int) data;
        System.out.println(resultInt);
        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumbercode(resultInt);
        //返回响应信息
        return ResponseResult.success(numberCodeResponse);
    }
}
