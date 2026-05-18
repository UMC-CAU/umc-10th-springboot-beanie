package com.example.umc_ch05_mission.global.security.service;

import com.example.umc_ch05_mission.domain.member.entity.Member;
import com.example.umc_ch05_mission.domain.member.exception.MemberException;
import com.example.umc_ch05_mission.domain.member.exception.code.MemberErrorCode;
import com.example.umc_ch05_mission.domain.member.repository.MemberRepository;
import com.example.umc_ch05_mission.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security가 로그인 시 사용하는 서비스
 * 이메일(username)로 회원을 조회해서 AuthMember(UserDetails) 형태로 반환
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // Member 엔티티를 AuthMember로 감싸서 반환
        return new AuthMember(member);
    }
}
