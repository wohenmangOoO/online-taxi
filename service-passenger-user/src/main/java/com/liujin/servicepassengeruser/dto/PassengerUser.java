package com.liujin.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PassengerUser {

    private Long id;

    private String passengerName;

    private String passengerPhone;

    private Byte passengerGender;

    private Byte state;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModifed;
}
