package com.example.umc_ch05_mission.domain.member.exception.code;

import com.example.umc_ch05_mission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberSuccessCode implements BaseSuccessCode {

    MEMBER_JOIN(HttpStatus.CREATED, "MEMBER201_1", "회원 가입이 완료되었습니다."),
    MEMBER_FOUND(HttpStatus.OK, "MEMBER200_1", "회원 조회에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus GetStatus() {
        return this.status;
    }
}
