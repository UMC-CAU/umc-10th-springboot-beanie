package com.example.umc_ch05_mission.global.security.entity;

import com.example.umc_ch05_mission.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Spring Security가 인증에 사용하는 UserDetails 구현체
 * Member 엔티티를 감싸서 Spring Security가 이해할 수 있는 형태로 변환
 *
 * 장점: @AuthenticationPrincipal AuthMember authMember 로
 *       컨트롤러에서 로그인한 회원 정보를 바로 꺼낼 수 있음
 */
@Getter
@RequiredArgsConstructor
public class AuthMember implements UserDetails {

    private final Member member;

    // 권한 목록 (현재는 별도 권한 없음)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    // Spring Security가 비밀번호 검증에 사용하는 메서드
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // Spring Security가 사용자 식별에 사용하는 메서드 (이메일을 username으로 사용)
    @Override
    public String getUsername() {
        return member.getEmail();
    }
}
