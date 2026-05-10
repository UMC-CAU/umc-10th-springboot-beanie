package com.example.umc_ch05_mission.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    // 내가 진행중인 미션 조회 요청 DTO (사용자 ID를 RequestBody로 받음, 하드코딩 X)
    @Getter
    public static class MyMissionsReq {
        @NotNull(message = "회원 ID는 필수입니다.")
        private Long memberId;

        @NotBlank(message = "미션 상태는 필수입니다. (ONGOING 또는 COMPLETE)")
        private String status;

        private Integer pageNumber = 0;
        private Integer pageSize = 10;
    }
}

