package com.example.umc_ch05_mission.domain.mission.service;

import com.example.umc_ch05_mission.domain.mission.dto.MissionReqDTO;
import com.example.umc_ch05_mission.domain.mission.dto.MissionResDTO;
import com.example.umc_ch05_mission.domain.mission.entity.Mission;
import com.example.umc_ch05_mission.domain.mission.enums.MissionStatus;
import com.example.umc_ch05_mission.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;

    @Override
    public Page<MissionResDTO.MissionItemRes> getHomeMissions(Long regionId, Pageable pageable) {
        // TODO: Spring Security 적용 후 SecurityContext에서 memberId 추출
        Long memberId = 1L;
        List<MissionStatus> excludedStatuses = List.of(MissionStatus.ONGOING, MissionStatus.PENDING);

        Page<Mission> missions = missionRepository.findAvailableMissions(regionId, memberId, excludedStatuses, pageable);

        return missions.map(m -> MissionResDTO.MissionItemRes.builder()
                .storeName(m.getStore().getName())
                .missionContent(m.getMissionSpec())
                .reward(m.getReward())
                .build());
    }

    @Override
    public void requestMissionSuccess(Long memberMissionId) {}

    @Override
    public MissionResDTO.VerificationCodeRes getVerificationCode(Long memberMissionId) { return null; }

    @Override
    public void confirmMission(Long memberMissionId, MissionReqDTO.ConfirmMissionReq req) {}
}
