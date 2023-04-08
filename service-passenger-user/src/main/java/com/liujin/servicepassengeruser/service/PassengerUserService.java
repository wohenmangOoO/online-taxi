package com.liujin.servicepassengeruser.service;

import com.liujin.internalcommon.dto.ResponseResult;
import com.liujin.servicepassengeruser.dto.PassengerUser;
import com.liujin.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PassengerUserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;
    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("user service被调用 手机号："+passengerPhone);

        //判断用户是否存在
        boolean checkUserExist = checkUserExist(passengerPhone);
        if (checkUserExist){
            System.out.println("用户存在");
        }else{
            System.out.println("用户不存在");
        }
        addUser(checkUserExist,passengerPhone);
        return ResponseResult.success();
    }
    private boolean checkUserExist(String passengerPhone){
        Map<String,Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        if(passengerUsers.size() == 0){
            return false;
        }
        System.out.println(passengerUsers.get(0));
        return true;
    }
    private void addUser(boolean user, String passengerPhone){
        if (!user){
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("李四");
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerGender((byte) 0);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModifed(now);
            passengerUser.setState((byte) 0);
            passengerUserMapper.insert(passengerUser);
        }
    }
}
