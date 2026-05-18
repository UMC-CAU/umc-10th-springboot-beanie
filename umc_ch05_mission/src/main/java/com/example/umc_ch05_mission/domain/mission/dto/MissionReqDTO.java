package com.example.umc_ch05_mission.domain.mission.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class MissionReqDTO {

    @Getter
    public static class ConfirmMissionReq {
        @NotBlank private String verificationCode;
    }
}

