package com.example.umc_ch05_mission.domain.mission.exception.code;

import com.example.umc_ch05_mission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    MISSION_CREATED(HttpStatus.CREATED, "MISSION201_1", "미션이 등록되었습니다."),
    MISSION_FOUND(HttpStatus.OK, "MISSION200_1", "미션 조회에 성공했습니다."),
    MISSION_CHALLENGED(HttpStatus.OK, "MISSION200_2", "미션 도전이 등록되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus GetStatus() {
        return this.status;
    }
}
