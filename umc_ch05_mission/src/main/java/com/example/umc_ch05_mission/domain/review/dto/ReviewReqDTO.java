package com.example.umc_ch05_mission.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ReviewReqDTO {

    @Getter
    public static class CreateReviewReq {
        @Min(1) @Max(5) private Integer rating;
        @NotBlank private String content;
    }
}
