package com.example.umc_ch05_mission.global.security.handler;

import com.example.umc_ch05_mission.global.apiPayload.ApiResponse;
import com.example.umc_ch05_mission.global.apiPayload.code.BaseErrorCode;
import com.example.umc_ch05_mission.global.apiPayload.code.GeneralErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 인가 실패(403) 핸들러
 * 로그인은 했지만 권한이 없는 리소스에 접근할 때 실행
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        BaseErrorCode code = GeneralErrorCode.FORBIDDEN;

        // 응답 Content-Type, HTTP 상태코드 정의
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.GetStatus().value());

        // Response Body에 응답통일한 객체를 넣기
        ApiResponse<Void> errorResponse = ApiResponse.onFailure(code, null);

        // 실제 Response로 덮어쓰기
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
