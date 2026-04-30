package com.backend.pishop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.pishop.entity.SerialNumber;
import com.backend.pishop.enums.SerialStatus;

public interface SerialNumberRepository
        extends JpaRepository<SerialNumber, String> {

    @Query("""
        SELECT s
        FROM SerialNumber s
        WHERE
            (:keyword IS NULL
                OR LOWER(s.serialNumber)
                    LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(s.product.modelName)
                    LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(s.product.modelNumber)
                    LIKE LOWER(CONCAT('%', :keyword, '%')))
        AND (:status IS NULL OR s.status = :status)
        ORDER BY s.createAt DESC
    """)
    List<SerialNumber> search(
            @Param("keyword") String keyword,
            @Param("status") SerialStatus status
    );
}