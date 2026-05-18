package com.example.umc_ch05_mission.domain.region.entity;

import com.example.umc_ch05_mission.domain.mission.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 지역명 (예: 안암동)

    @OneToMany(mappedBy = "region")
    private List<Store> stores = new ArrayList<>();
}
