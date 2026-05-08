package com.example.umc_ch05_mission.domain.review.controller;

import com.example.umc_ch05_mission.domain.review.dto.ReviewReqDTO;
import com.example.umc_ch05_mission.domain.review.dto.ReviewResDTO;
import com.example.umc_ch05_mission.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc_ch05_mission.domain.review.service.ReviewService;
import com.example.umc_ch05_mission.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Review", description = "리뷰 관련 API")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 작성",
               description = "특정 가게에 별점(1~5)과 내용으로 리뷰를 작성합니다. 같은 가게에 중복 작성은 불가합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "리뷰 작성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 유효성 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "가게 또는 회원 없음"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 작성한 리뷰 존재")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/stores/{storeId}/reviews")
    public ApiResponse<ReviewResDTO.ReviewItemRes> createReview(
            @Parameter(description = "가게 ID", example = "1", required = true)
            @PathVariable Long storeId,
            @Valid @RequestBody ReviewReqDTO.CreateReviewReq req) {
        // TODO: Spring Security 적용 후 @AuthenticationPrincipal로 memberId 주입
        return ApiResponse.of(ReviewSuccessCode.REVIEW_CREATED,
                reviewService.writeReview(1L, storeId, req.getRating(), req.getContent()));
    }
}
