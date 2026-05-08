package com.example.umc_ch05_mission.global.apiPayload.exception;

import com.example.umc_ch05_mission.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

/*
 * [워크북과의 차이]
 * 워크북: CustomException(ErrorCode errorCode) — ErrorCode가 단일 enum
 * 현재:   ProjectException(BaseErrorCode errorCode) — BaseErrorCode 인터페이스를 받음
 *
 * 이유: 모든 에러 코드를 하나의 enum에 모으면 도메인이 늘어날수록 파일이 비대해짐.
 *       BaseErrorCode 인터페이스를 두고 도메인별 enum(MemberErrorCode, MissionErrorCode 등)이
 *       각자 구현하도록 해서 관심사를 분리함. GlobalExceptionHandler는 ProjectException 하나만
 *       처리하면 되므로 핸들러 코드는 동일하게 단순하게 유지됨.
 *
 * 도메인별 예외(MemberException extends ProjectException)를 두는 이유:
 *       스택 트레이스에서 예외의 도메인 출처를 바로 확인할 수 있고,
 *       도메인별로 추가적인 필드나 로직이 필요할 때 확장이 쉬움.
 */
@Getter
public class ProjectException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public ProjectException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
