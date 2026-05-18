package com.example.umc_ch05_mission.global.security;

import com.example.umc_ch05_mission.domain.member.entity.Member;
import com.example.umc_ch05_mission.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security가 로그인 시 사용하는 서비스
 * 이메일(username)로 회원을 조회해서 UserDetails 형태로 반환
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 회원을 찾을 수 없습니다: " + email));

        // Spring Security가 인식하는 UserDetails 형태로 변환
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())  // BCrypt로 암호화된 비밀번호
                .roles("USER")                   // 기본 권한
                .build();
    }
}
