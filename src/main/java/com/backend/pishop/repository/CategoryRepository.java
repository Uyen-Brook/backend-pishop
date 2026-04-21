package com.backend.pishop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findAllByOrderByNameAsc();

    Optional<Category> findById(Long id); // NÊN khai báo generic
    
    @Query("""
            SELECT c FROM Category c
            WHERE 
                (:keyword IS NULL OR 
                 LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                 LOWER(c.note) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                 STR(c.id) LIKE CONCAT('%', :keyword, '%')
                )
        """)
        List<Category> search(@Param("keyword") String keyword);
}