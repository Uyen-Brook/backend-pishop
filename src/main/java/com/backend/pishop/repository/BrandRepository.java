package com.backend.pishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	List<Brand> findAllByOrderByNameAsc();
}