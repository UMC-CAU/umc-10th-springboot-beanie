package com.example.umc_ch05_mission.domain.review.repository;

import com.example.umc_ch05_mission.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰 작성 전 중복 검증: 같은 회원이 같은 가게에 이미 리뷰를 작성했는지 확인
    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.member.id = :memberId AND r.store.id = :storeId")
    boolean existsByMemberIdAndStoreId(@Param("memberId") Long memberId, @Param("storeId") Long storeId);
}
