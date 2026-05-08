package com.example.umc_ch05_mission.domain.mission.entity;

import com.example.umc_ch05_mission.domain.mission.entity.mapping.MemberMission;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer reward; // 지급 포인트

    private Integer minPrice; // 조건 금액

    @Column(columnDefinition = "TEXT")
    private String missionSpec; // 미션 설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "mission")
    private List<MemberMission> memberMissions = new ArrayList<>();
}
