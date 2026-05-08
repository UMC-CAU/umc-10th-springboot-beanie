package com.example.umc_ch05_mission.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ReviewResDTO {

    @Getter @Builder
    public static class ReviewItemRes {
        private Long reviewId;
        private String storeName;
        private Integer rating;
        private String content;
        private LocalDate createdAt;
    }

    @Getter @Builder
    public static class ReviewListRes {
        private List<ReviewItemRes> reviews;
    }
}
