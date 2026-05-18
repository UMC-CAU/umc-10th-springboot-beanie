package com.example.umc_ch05_mission.domain.mission.repository;

import com.example.umc_ch05_mission.domain.mission.entity.Mission;
import com.example.umc_ch05_mission.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 홈화면 도전 가능한 미션 목록: 해당 지역의 미션 중 회원이 아직 도전하지 않은 것만 반환
    // statuses 파라미터로 제외할 상태를 외부에서 주입 → 서비스에서 ONGOING, PENDING 전달
    @Query(
        value = "SELECT m FROM Mission m " +
                "JOIN m.store s " +
                "WHERE s.region.id = :regionId " +
                "AND m.id NOT IN (" +
                "    SELECT mm.mission.id FROM MemberMission mm " +
                "    WHERE mm.member.id = :memberId AND mm.status IN :statuses" +
                ")",
        countQuery = "SELECT COUNT(m) FROM Mission m " +
                "JOIN m.store s " +
                "WHERE s.region.id = :regionId " +
                "AND m.id NOT IN (" +
                "    SELECT mm.mission.id FROM MemberMission mm " +
                "    WHERE mm.member.id = :memberId AND mm.status IN :statuses" +
                ")"
    )
    Page<Mission> findAvailableMissions(
            @Param("regionId") Long regionId,
            @Param("memberId") Long memberId,
            @Param("statuses") List<MissionStatus> statuses,
            Pageable pageable);
}
