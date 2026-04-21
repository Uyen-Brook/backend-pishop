package com.backend.pishop.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.pishop.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier>{
	List<Supplier> findAllByOrderByNameDesc();

    @Query("""
        SELECT s FROM Supplier s
        WHERE 
            (:keyword IS NULL OR
             LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
             LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
             LOWER(s.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
             LOWER(s.note) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
             STR(s.id) LIKE CONCAT('%', :keyword, '%')
            )
    """)
    List<Supplier> search(@Param("keyword") String keyword);
}
