package com.example.umc_ch05_mission.global.config;

import com.example.umc_ch05_mission.global.security.handler.CustomAccessDeniedHandler;
import com.example.umc_ch05_mission.global.security.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    // 인증 없이 접근 가능한 URI 목록 (Public API)
    private final String[] allowUris = {
            // Swagger 허용
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            // 회원가입, 로그인 허용
            "/auth/**",
            "/signin"
    };

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
     * Public API (로그인 없이 접근 가능): allowUris 에 포함된 경로
     * Private API (로그인 필요): 그 외 모든 요청
     *
     * formLogin: Spring Security 기본 로그인 폼 사용
     *   → 로그인 성공 시 Swagger로 리다이렉트
     * logout: /logout 으로 로그아웃
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // 요청별 인증/인가 설정
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()   // Public API
                        .anyRequest().authenticated()              // Private API
                )

                // 폼 로그인 설정: 로그인 성공 시 Swagger로 이동
                .formLogin(form -> form
                        .defaultSuccessUrl("/swagger-ui/index.html", true)
                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // 인증/인가 실패 시 통일된 JSON 응답
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)  // 401: 미인증
                        .accessDeniedHandler(accessDeniedHandler)             // 403: 권한 없음
                );

        return http.build();
    }
}
