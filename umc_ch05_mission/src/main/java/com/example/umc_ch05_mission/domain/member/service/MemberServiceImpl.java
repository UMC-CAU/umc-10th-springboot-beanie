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
import com.example.umc_ch05_mission.domain.review.entity.Review;
import com.example.umc_ch05_mission.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;

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

    /**
     * 내가 생성한 리뷰 목록 조회 - 커서 기반 페이지네이션
     * query: "id" → ID 내림차순, "rating" → 별점 내림차순 + ID 내림차순
     * cursor: 첫 요청은 "-1", 이후 응답의 nextCursor 값을 그대로 전달
     *   - id 정렬 시 cursor 형식: "10" (id값)
     *   - rating 정렬 시 cursor 형식: "4:10" (rating:id)
     */
    @Override
    public ReviewResDTO.CursorPagination<ReviewResDTO.ReviewItemRes> getMyReviews(
            Long memberId, Integer pageSize, String cursor, String query) {

        // 페이지 번호는 항상 0 (커서가 위치를 정해주므로)
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        Slice<Review> reviewSlice;

        if (!cursor.equals("-1")) {
            // 커서가 있는 경우: 커서 분리 후 where 절에 조건 추가
            String[] parts = cursor.split(":");
            switch (query.toLowerCase()) {
                case "id" -> {
                    Long idCursor = Long.parseLong(parts[0]);
                    reviewSlice = reviewRepository.findByMemberIdAndIdLessThanOrderByIdDesc(
                            memberId, idCursor, pageRequest);
                }
                case "rating" -> {
                    Integer ratingCursor = Integer.parseInt(parts[0]);
                    Long idCursor = Long.parseLong(parts[1]);
                    reviewSlice = reviewRepository.findByMemberIdWithRatingCursor(
                            memberId, ratingCursor, idCursor, pageRequest);
                }
                default -> throw new MemberException(MemberErrorCode.INVALID_QUERY);
            }
        } else {
            // 첫 요청: 커서 없이 전체 조회
            switch (query.toLowerCase()) {
                case "id" ->
                    reviewSlice = reviewRepository.findByMemberIdOrderByIdDesc(memberId, pageRequest);
                case "rating" ->
                    reviewSlice = reviewRepository.findByMemberIdOrderByRatingDescIdDesc(memberId, pageRequest);
                default -> throw new MemberException(MemberErrorCode.INVALID_QUERY);
            }
        }

        // 다음 커서 계산: 현재 페이지의 마지막 데이터 기준
        String nextCursor;
        if (reviewSlice.hasContent()) {
            Review last = reviewSlice.getContent().get(reviewSlice.getContent().size() - 1);
            nextCursor = query.equalsIgnoreCase("id")
                    ? last.getId().toString()
                    : last.getRating() + ":" + last.getId();
        } else {
            nextCursor = "-1"; // 더 이상 데이터 없음
        }

        // Review 엔티티 → ReviewItemRes DTO 변환 (사진 제외)
        List<ReviewResDTO.ReviewItemRes> data = reviewSlice.getContent().stream()
                .map(r -> ReviewResDTO.ReviewItemRes.builder()
                        .reviewId(r.getId())
                        .storeName(r.getStore().getName())
                        .rating(r.getRating())
                        .content(r.getBody())
                        .createdAt(LocalDate.now()) // createdAt 컬럼 미구현 → 현재 날짜로 대체
                        .build())
                .toList();

        return ReviewResDTO.CursorPagination.<ReviewResDTO.ReviewItemRes>builder()
                .data(data)
                .hasNext(reviewSlice.hasNext())
                .nextCursor(nextCursor)
                .pageSize(reviewSlice.getSize())
                .build();
    }
}
