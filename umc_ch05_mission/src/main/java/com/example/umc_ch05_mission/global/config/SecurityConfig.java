package com.example.umc_ch05_mission.global.config;

import com.example.umc_ch05_mission.global.security.handler.CustomAccessDeniedHandler;
import com.example.umc_ch05_mission.global.security.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * BCrypt 비밀번호 인코더 Bean
     * 회원가입 시 비밀번호를 솔트(salt) 처리하여 안전하게 저장
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Security 필터 체인 설정
     *
     * Public API (로그인 없이 접근 가능):
     *   - POST /signin         → 회원가입
     *   - POST /auth/login     → 로그인
     *   - Swagger UI, API Docs → 개발 편의
     *   - H2 Console          → 개발 편의
     *
     * Private API (로그인 필요):
     *   - 그 외 모든 요청
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // REST API이므로 CSRF 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // REST API이므로 세션 사용 안 함 (Stateless)
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 요청별 인증/인가 설정
                .authorizeHttpRequests(auth -> auth
                        // Public API: 회원가입, 로그인
                        .requestMatchers(HttpMethod.POST, "/signin").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        // Public API: Swagger (개발 편의)
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Public API: H2 Console (개발 편의)
                        .requestMatchers("/h2-console/**").permitAll()

                        // 그 외 모든 요청 → 로그인 필요 (Private API)
                        .anyRequest().authenticated()
                )

                // HTTP Basic 인증 (이메일:비밀번호로 로그인)
                // Authorization: Basic base64(email:password) 헤더로 인증
                .httpBasic(basic ->
                        basic.authenticationEntryPoint(authenticationEntryPoint))

                // 인증/인가 실패 시 통일된 JSON 응답
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)  // 401: 미인증
                        .accessDeniedHandler(accessDeniedHandler)             // 403: 권한 없음
                );

        return http.build();
    }
}
