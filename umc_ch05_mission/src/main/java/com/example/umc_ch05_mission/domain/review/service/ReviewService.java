package com.example.umc_ch05_mission.domain.review.service;

import com.example.umc_ch05_mission.domain.review.dto.ReviewResDTO;

public interface ReviewService {

    ReviewResDTO.ReviewItemRes writeReview(Long memberId, Long storeId, int rating, String content);
}
