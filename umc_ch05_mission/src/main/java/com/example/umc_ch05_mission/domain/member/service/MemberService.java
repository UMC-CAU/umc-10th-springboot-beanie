package com.example.umc_ch05_mission.domain.member.service;

import com.example.umc_ch05_mission.domain.member.dto.MemberReqDTO;
import com.example.umc_ch05_mission.domain.member.dto.MemberResDTO;
import com.example.umc_ch05_mission.domain.mission.dto.MissionResDTO;
import com.example.umc_ch05_mission.domain.review.dto.ReviewResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberService {

    MemberResDTO.SignUpRes signUp(MemberReqDTO.SignUpReq req);

    MemberResDTO.LoginRes login(MemberReqDTO.LoginReq req);

    void updateMyInfo(Long memberId, MemberReqDTO.UpdateMemberReq req);

    MemberResDTO.HomeProgressRes getHomeProgress(Long memberId, String region);

    MemberResDTO.MyPageRes getMyPageInfo(Long memberId);

    Page<MissionResDTO.MissionItemRes> getMyMissions(Long memberId, String status, Pageable pageable);

    ReviewResDTO.CursorPagination<ReviewResDTO.ReviewItemRes> getMyReviews(
            Long memberId, Integer pageSize, String cursor, String query);
}
