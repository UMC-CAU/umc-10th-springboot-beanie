package com.example.umc_ch05_mission.domain.mission.exception.code;

import com.example.umc_ch05_mission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode implements BaseErrorCode {

    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_1", "해당 미션을 찾을 수 없습니다."),
    MISSION_ALREADY_CHALLENGED(HttpStatus.CONFLICT, "MISSION409_1", "이미 도전 중인 미션입니다."),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_2", "해당 가게를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus GetStatus() {
        return this.status;
    }
}
