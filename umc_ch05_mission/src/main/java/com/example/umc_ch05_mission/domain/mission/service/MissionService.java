package com.example.umc_ch05_mission.domain.mission.service;

import com.example.umc_ch05_mission.domain.mission.dto.MissionReqDTO;
import com.example.umc_ch05_mission.domain.mission.dto.MissionResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MissionService {

    Page<MissionResDTO.MissionItemRes> getHomeMissions(Long regionId, Pageable pageable);

    void requestMissionSuccess(Long memberMissionId);

    MissionResDTO.VerificationCodeRes getVerificationCode(Long memberMissionId);

    void confirmMission(Long memberMissionId, MissionReqDTO.ConfirmMissionReq req);
}
