package com.example.umc_ch05_mission.domain.mission.exception;

import com.example.umc_ch05_mission.global.apiPayload.code.BaseErrorCode;
import com.example.umc_ch05_mission.global.apiPayload.exception.ProjectException;

public class MissionException extends ProjectException {
    public MissionException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
