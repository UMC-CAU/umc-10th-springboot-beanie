package com.example.umc_ch05_mission.domain.review.service;

import com.example.umc_ch05_mission.domain.member.entity.Member;
import com.example.umc_ch05_mission.domain.member.exception.MemberException;
import com.example.umc_ch05_mission.domain.member.exception.code.MemberErrorCode;
import com.example.umc_ch05_mission.domain.member.repository.MemberRepository;
import com.example.umc_ch05_mission.domain.mission.entity.Store;
import com.example.umc_ch05_mission.domain.mission.exception.MissionException;
import com.example.umc_ch05_mission.domain.mission.exception.code.MissionErrorCode;
import com.example.umc_ch05_mission.domain.mission.repository.StoreRepository;
import com.example.umc_ch05_mission.domain.review.dto.ReviewResDTO;
import com.example.umc_ch05_mission.domain.review.entity.Review;
import com.example.umc_ch05_mission.domain.review.exception.ReviewException;
import com.example.umc_ch05_mission.domain.review.exception.code.ReviewErrorCode;
import com.example.umc_ch05_mission.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Override
    public ReviewResDTO.ReviewItemRes writeReview(Long memberId, Long storeId, int rating, String content) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.STORE_NOT_FOUND));

        if (reviewRepository.existsByMemberIdAndStoreId(memberId, storeId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_ALREADY_EXISTS);
        }

        Review review = Review.builder()
                .member(member)
                .store(store)
                .rating(rating)
                .body(content)
                .build();

        Review saved = reviewRepository.save(review);

        return ReviewResDTO.ReviewItemRes.builder()
                .reviewId(saved.getId())
                .storeName(store.getName())
                .rating(saved.getRating())
                .content(saved.getBody())
                .createdAt(LocalDate.now())
                .build();
    }
}
