package com.backend.pishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.AdministrativeRegion;

@Repository
public interface AdministrativeRegionRepository extends JpaRepository<AdministrativeRegion, Integer> {
}