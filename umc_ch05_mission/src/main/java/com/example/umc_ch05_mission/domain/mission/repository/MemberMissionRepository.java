package com.example.umc_ch05_mission.domain.mission.repository;

import com.example.umc_ch05_mission.domain.mission.entity.mapping.MemberMission;
import com.example.umc_ch05_mission.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 내 미션 목록 조회: memberId + status 필터, 미션명·가게명·포인트·상태 포함
    // countQuery를 분리한 이유: 페이징 count 쿼리에는 JOIN이 불필요해 성능 최적화
    @Query(
        value = "SELECT mm FROM MemberMission mm " +
                "JOIN mm.mission m " +
                "JOIN m.store s " +
                "WHERE mm.member.id = :memberId AND mm.status = :status",
        countQuery = "SELECT COUNT(mm) FROM MemberMission mm " +
                     "WHERE mm.member.id = :memberId AND mm.status = :status"
    )
    Page<MemberMission> findByMemberIdAndStatus(
            @Param("memberId") Long memberId,
            @Param("status") MissionStatus status,
            Pageable pageable);
}
