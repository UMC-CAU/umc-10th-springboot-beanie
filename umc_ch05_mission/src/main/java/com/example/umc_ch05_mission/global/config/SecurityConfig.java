package com.example.umc_ch05_mission.global.config;

import com.example.umc_ch05_mission.global.security.handler.CustomAccessDeniedHandler;
import com.example.umc_ch05_mission.global.security.handler.CustomAuthenticationEntryPoint;
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
public class SecurityConfig {

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 403 핸들러 Bean 등록
    @Bean
    public CustomAccessDeniedHandler customAccessDenied() {
        return new CustomAccessDeniedHandler();
    }

    // 401 핸들러 Bean 등록
    @Bean
    public CustomAuthenticationEntryPoint customEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

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

                // 예외 상황 핸들러
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDenied())         // 403: 권한 없음
                        .authenticationEntryPoint(customEntryPoint())      // 401: 미인증
                );

        return http.build();
    }
}
