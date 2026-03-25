package com.backend.pishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Ward;

@Repository
public interface WardRepository extends JpaRepository<Ward, String> {

    List<Ward> findByProvince_Code(String provinceCode);
}
