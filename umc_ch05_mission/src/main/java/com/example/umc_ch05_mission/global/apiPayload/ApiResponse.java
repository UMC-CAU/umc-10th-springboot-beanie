package com.example.umc_ch05_mission.global.apiPayload;

import com.example.umc_ch05_mission.global.apiPayload.code.BaseErrorCode;
import com.example.umc_ch05_mission.global.apiPayload.code.BaseSuccessCode;
import com.example.umc_ch05_mission.global.apiPayload.code.GeneralSuccessCode; // 이름 확인!
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private final String code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 성공한 경우 응답 생성
    public static <T> ApiResponse<T> onSuccess(T result) {
        // SuccessStatus 대신 수빈님이 만든 GeneralSuccessCode 사용
        return new ApiResponse<>(true, GeneralSuccessCode.OK.getCode(), GeneralSuccessCode.OK.getMessage(), result);
    }

    // 성공한 경우 (Custom SuccessCode 사용 시)
    public static <T> ApiResponse<T> of(BaseSuccessCode code, T result) {
        return new ApiResponse<>(true, code.getCode(), code.getMessage(), result);
    }

    // 실패한 경우 (BaseErrorCode 사용)
    public static <T> ApiResponse<T> onFailure(BaseErrorCode code) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), null);
    }

    public static <T> ApiResponse<T> onFailure(BaseErrorCode code, T data) {
        return new ApiResponse<>(false, code.getCode(), code.getMessage(), data);
    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }
}