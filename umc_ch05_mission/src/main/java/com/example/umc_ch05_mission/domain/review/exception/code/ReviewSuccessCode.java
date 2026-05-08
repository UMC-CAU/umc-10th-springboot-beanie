package com.example.umc_ch05_mission.domain.review.exception.code;

import com.example.umc_ch05_mission.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode {

    REVIEW_CREATED(HttpStatus.CREATED, "REVIEW201_1", "리뷰가 등록되었습니다."),
    REVIEW_FOUND(HttpStatus.OK, "REVIEW200_1", "리뷰 조회에 성공했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus GetStatus() {
        return this.status;
    }
}
