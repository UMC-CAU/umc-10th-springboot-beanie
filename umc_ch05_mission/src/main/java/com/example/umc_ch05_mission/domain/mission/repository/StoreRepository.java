package com.example.umc_ch05_mission.domain.mission.repository;

import com.example.umc_ch05_mission.domain.mission.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
