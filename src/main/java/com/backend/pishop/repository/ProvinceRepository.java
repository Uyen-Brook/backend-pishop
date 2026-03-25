package com.backend.pishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {

    List<Province> findByRegion_Id(Integer regionId);
}