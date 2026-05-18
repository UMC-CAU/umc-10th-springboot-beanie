package com.example.umc_ch05_mission.global.security.handler;

import com.example.umc_ch05_mission.global.apiPayload.code.GeneralErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 인증 실패(401) 핸들러
 * 로그인하지 않은 사용자가 Private API에 접근할 때 실행
 * → 기존 ProjectException 처리 방식과 동일한 JSON 형식으로 응답
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        GeneralErrorCode errorCode = GeneralErrorCode.UNAUTHORIZED;

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> body = Map.of(
                "isSuccess", false,
                "code", errorCode.getCode(),
                "message", "로그인이 필요합니다."
        );

        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}
