package com.backend.pishop.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.pishop.entity.WarrantyAcceptance;

public interface WarrantyAcceptanceRepository extends JpaRepository<WarrantyAcceptance, Long> {

    @Query("""
        SELECT w
        FROM WarrantyAcceptance w
        WHERE
            (:keyword IS NULL
                OR LOWER(w.customerName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(w.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(w.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(w.serialNumberRef.serialNumber)
                    LIKE LOWER(CONCAT('%', :keyword, '%')))
        ORDER BY w.startDate DESC
    """)
    List<WarrantyAcceptance> search(
            @Param("keyword") String keyword
    );
}