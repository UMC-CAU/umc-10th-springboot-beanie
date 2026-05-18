package com.example.umc_ch05_mission.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberResDTO {

    @Getter @Builder
    public static class SignUpRes {
        private Long memberId;
    }

    @Getter @Builder
    public static class LoginRes {
        private Long memberId;
        private String name;
        private String email;
        // TODO: JWT 토큰 필드 추가 예정 (Spring Security 적용 시)
    }

    @Getter @Builder
    public static class MemberInfoRes {
        private String name;
        private String profileImage;
        private String email;
        private String phoneNumber;
    }

    @Getter @Builder
    public static class HomeProgressRes {
        private Integer ongoingMissions;
        private Integer points;
        private String region;
    }

    @Getter @Builder
    public static class MyPageRes {
        private String name;
        private String email;
        private Integer points;
        private Long reviewCount;
    }
}

