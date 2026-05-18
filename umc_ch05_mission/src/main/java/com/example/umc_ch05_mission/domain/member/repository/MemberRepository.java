package com.example.umc_ch05_mission.domain.member.repository;

import com.example.umc_ch05_mission.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 마이페이지 조회용 Projection: 닉네임·이메일·포인트·리뷰 수를 한 번에 조회
    // LEFT JOIN으로 리뷰 미작성 회원도 reviewCount=0으로 포함
    interface MyPageInfo {
        String getName();
        String getEmail();
        Integer getPoints();
        Long getReviewCount();
    }

    @Query("SELECT m.name AS name, m.email AS email, m.points AS points, COUNT(r) AS reviewCount " +
           "FROM Member m LEFT JOIN m.reviews r " +
           "WHERE m.id = :memberId " +
           "GROUP BY m.id, m.name, m.email, m.points")
    Optional<MyPageInfo> findMyPageInfo(@Param("memberId") Long memberId);

    Optional<Member> findByEmail(String email);
}
