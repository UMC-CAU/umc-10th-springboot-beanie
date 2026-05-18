package com.example.umc_ch05_mission.domain.member.converter;

import com.example.umc_ch05_mission.domain.member.dto.MemberReqDTO;
import com.example.umc_ch05_mission.domain.member.dto.MemberResDTO;
import com.example.umc_ch05_mission.domain.member.entity.Member;
import com.example.umc_ch05_mission.domain.member.enums.Gender;
import com.example.umc_ch05_mission.domain.member.enums.SocialType;

public class MemberConverter {

    /**
     * 회원가입 요청 DTO → Member 엔티티
     * 비밀번호는 이미 BCrypt로 인코딩된 값을 받음
     */
    public static Member toMember(MemberReqDTO.SignUpReq req, String encodedPassword) {
        return Member.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(encodedPassword)    // BCrypt 인코딩된 비밀번호
                .phoneNumber(req.getPhoneNumber())
                .points(0)
                .gender(Gender.NONE)
                .socialType(SocialType.NONE)
                .build();
    }

    /**
     * 저장된 Member 엔티티 → 회원가입 응답 DTO
     */
    public static MemberResDTO.SignUpRes toSignUpRes(Member member) {
        return MemberResDTO.SignUpRes.builder()
                .memberId(member.getId())
                .build();
    }
}
