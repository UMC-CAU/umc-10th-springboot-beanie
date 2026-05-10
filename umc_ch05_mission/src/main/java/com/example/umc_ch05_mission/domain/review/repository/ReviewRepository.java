package com.example.umc_ch05_mission.domain.review.repository;

import com.example.umc_ch05_mission.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰 작성 전 중복 검증: 같은 회원이 같은 가게에 이미 리뷰를 작성했는지 확인
    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.member.id = :memberId AND r.store.id = :storeId")
    boolean existsByMemberIdAndStoreId(@Param("memberId") Long memberId, @Param("storeId") Long storeId);

    // === 커서 기반 페이지네이션: ID 순 ===

    // ID 순 - 첫 페이지 (커서 없음)
    Slice<Review> findByMemberIdOrderByIdDesc(Long memberId, Pageable pageable);

    // ID 순 - 커서 있음 (해당 id보다 작은 것만 조회)
    Slice<Review> findByMemberIdAndIdLessThanOrderByIdDesc(Long memberId, Long idCursor, Pageable pageable);

    // === 커서 기반 페이지네이션: 별점 순 ===

    // 별점 순 - 첫 페이지 (커서 없음): 별점 내림차순, 같은 별점이면 id 내림차순
    Slice<Review> findByMemberIdOrderByRatingDescIdDesc(Long memberId, Pageable pageable);

    // 별점 순 - 커서 있음: 별점이 커서보다 낮거나, 같은 별점이면 id가 커서보다 작은 것
    @Query("SELECT r FROM Review r " +
           "WHERE r.member.id = :memberId " +
           "AND (r.rating < :ratingCursor OR (r.rating = :ratingCursor AND r.id < :idCursor)) " +
           "ORDER BY r.rating DESC, r.id DESC")
    Slice<Review> findByMemberIdWithRatingCursor(
            @Param("memberId") Long memberId,
            @Param("ratingCursor") Integer ratingCursor,
            @Param("idCursor") Long idCursor,
            Pageable pageable);
}
