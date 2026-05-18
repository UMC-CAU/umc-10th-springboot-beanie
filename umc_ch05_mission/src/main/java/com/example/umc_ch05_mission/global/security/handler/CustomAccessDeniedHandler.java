package com.example.umc_ch05_mission.global.security.handler;

import com.example.umc_ch05_mission.global.apiPayload.code.GeneralErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 인가 실패(403) 핸들러
 * 로그인은 했지만 권한이 없는 리소스에 접근할 때 실행
 * → 기존 ProjectException 처리 방식과 동일한 JSON 형식으로 응답
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        GeneralErrorCode errorCode = GeneralErrorCode.FORBIDDEN;

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> body = Map.of(
                "isSuccess", false,
                "code", errorCode.getCode(),
                "message", "접근 권한이 없습니다."
        );

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
