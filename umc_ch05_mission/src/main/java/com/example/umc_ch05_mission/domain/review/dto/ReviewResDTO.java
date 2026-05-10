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

    // 커서 기반 페이지네이션 응답 틀 (제네릭으로 어떤 타입이든 재사용 가능)
    @Getter @Builder
    public static class CursorPagination<T> {
        private List<T> data;        // 실제 데이터 목록
        private Boolean hasNext;     // 다음 페이지 존재 여부
        private String nextCursor;   // 다음 요청에 사용할 커서 값
        private Integer pageSize;    // 페이지 크기
    }
}
