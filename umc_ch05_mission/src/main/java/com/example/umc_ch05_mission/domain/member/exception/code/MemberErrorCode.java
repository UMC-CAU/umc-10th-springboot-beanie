package com.example.umc_ch05_mission.domain.member.exception.code;

import com.example.umc_ch05_mission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "해당 회원을 찾을 수 없습니다."),
    MEMBER_ALREADY_EXISTS(HttpStatus.CONFLICT, "MEMBER409_1", "이미 존재하는 회원입니다."),
    INVALID_MEMBER_STATUS(HttpStatus.BAD_REQUEST, "MEMBER400_1", "유효하지 않은 회원 상태입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus GetStatus() {
        return this.status;
    }
}
