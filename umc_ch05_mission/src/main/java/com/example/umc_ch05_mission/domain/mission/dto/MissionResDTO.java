package com.example.umc_ch05_mission.domain.mission.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    @Getter @Builder
    public static class MissionItemRes {
        private Long memberMissionId;
        private String storeName;
        private String missionContent;
        private Integer reward;
        private String status;
        private Integer daysLeft;
    }

    @Getter @Builder
    public static class MissionListRes {
        private List<MissionItemRes> missions;
    }

    @Getter @Builder
    public static class VerificationCodeRes {
        private String code;
        private LocalDateTime expiresAt;
    }
}

