package com.example.umc_ch05_mission.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    HttpStatus GetStatus();
    String getCode();
    String getMessage();

}
