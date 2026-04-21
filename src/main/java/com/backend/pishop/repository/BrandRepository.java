package com.backend.pishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	List<Brand> findAllByOrderByNameAsc();
	@Query(value = """
	        SELECT * FROM brands b
	        WHERE 
	            (:keyword IS NULL OR 
	             LOWER(b.brand_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
	             OR CAST(b.id AS CHAR) LIKE CONCAT('%', :keyword, '%'))
	    """, nativeQuery = true)
	    List<Brand> search(@Param("keyword") String keyword);
}
