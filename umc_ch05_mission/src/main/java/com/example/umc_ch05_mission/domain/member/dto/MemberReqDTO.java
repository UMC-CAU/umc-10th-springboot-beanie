package com.example.umc_ch05_mission.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class MemberReqDTO {

    @Getter
    public static class SignUpReq {
        @NotBlank private String name;
        @NotBlank @Email private String email;
        @NotBlank private String password;
        @NotBlank private String phoneNumber;
    }

    @Getter
    public static class LoginReq {
        @NotBlank @Email private String email;
        @NotBlank private String password;
    }

    // PATCH이므로 모든 필드는 선택적으로 수정 가능 → validation 없이 null 허용
    @Getter
    public static class UpdateMemberReq {
        private String name;
        private String phoneNumber;
        private String profileImage;
    }
}

