package com.example.umc_ch05_mission.domain.member.exception;

import com.example.umc_ch05_mission.global.apiPayload.code.BaseErrorCode;
import com.example.umc_ch05_mission.global.apiPayload.exception.ProjectException;

public class MemberException extends ProjectException {
    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
