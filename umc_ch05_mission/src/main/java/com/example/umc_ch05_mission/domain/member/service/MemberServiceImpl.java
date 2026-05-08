package com.example.umc_ch05_mission.domain.member.service;

import com.example.umc_ch05_mission.domain.member.dto.MemberReqDTO;
import com.example.umc_ch05_mission.domain.member.dto.MemberResDTO;
import com.example.umc_ch05_mission.domain.member.exception.MemberException;
import com.example.umc_ch05_mission.domain.member.exception.code.MemberErrorCode;
import com.example.umc_ch05_mission.domain.member.repository.MemberRepository;
import com.example.umc_ch05_mission.domain.mission.dto.MissionResDTO;
import com.example.umc_ch05_mission.domain.mission.entity.mapping.MemberMission;
import com.example.umc_ch05_mission.domain.mission.enums.MissionStatus;
import com.example.umc_ch05_mission.domain.mission.repository.MemberMissionRepository;
import com.example.umc_ch05_mission.domain.review.dto.ReviewResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;

    @Override
    public MemberResDTO.MyPageRes getMyPageInfo(Long memberId) {
        MemberRepository.MyPageInfo info = memberRepository.findMyPageInfo(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberResDTO.MyPageRes.builder()
                .name(info.getName())
                .email(info.getEmail())
                .points(info.getPoints())
                .reviewCount(info.getReviewCount())
                .build();
    }

    @Override
    public Page<MissionResDTO.MissionItemRes> getMyMissions(Long memberId, String status, Pageable pageable) {
        MissionStatus missionStatus = MissionStatus.valueOf(status.toUpperCase());
        Page<MemberMission> page = memberMissionRepository.findByMemberIdAndStatus(memberId, missionStatus, pageable);

        return page.map(mm -> MissionResDTO.MissionItemRes.builder()
                .memberMissionId(mm.getId())
                .storeName(mm.getMission().getStore().getName())
                .missionContent(mm.getMission().getMissionSpec())
                .reward(mm.getMission().getReward())
                .status(mm.getStatus().name())
                .build());
    }

    @Override
    public MemberResDTO.SignUpRes signUp(MemberReqDTO.SignUpReq req) { return null; }

    @Override
    public MemberResDTO.LoginRes login(MemberReqDTO.LoginReq req) { return null; }

    @Override
    public void updateMyInfo(Long memberId, MemberReqDTO.UpdateMemberReq req) {}

    @Override
    public MemberResDTO.HomeProgressRes getHomeProgress(Long memberId, String region) { return null; }

    @Override
    public ReviewResDTO.ReviewListRes getMyReviews(Long memberId) { return null; }
}
