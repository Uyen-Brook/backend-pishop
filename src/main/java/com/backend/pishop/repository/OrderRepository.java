package com.backend.pishop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.pishop.entity.Order;
import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.enums.PayStatus;
import com.backend.pishop.enums.PaymentMethod;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // =====================================================
    // SEARCH (PAGING VERSION)
    // =====================================================
    @Query("""
        SELECT o
        FROM Order o
        WHERE
            (:keyword IS NULL
                OR LOWER(o.toName) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(o.toPhone) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(o.trackingNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
            AND (:status IS NULL OR o.orderStatus = :status)
            AND (:accountId IS NULL OR o.account.id = :accountId)
    """)
    Page<Order> searchOrders(
            @Param("keyword") String keyword,
            @Param("status") OrderStatus status,
            @Param("accountId") Long accountId,
            Pageable pageable
    );

    // =====================================================
    // FIXED SORT QUERIES (JPQL => createdAt)
    // =====================================================
    List<Order> findByAccountIdOrderByCreatedAtDesc(Long accountId);

    List<Order> findByOrderStatusOrderByCreatedAtDesc(OrderStatus status);

    List<Order> findByAccountIdAndPaymentMethodAndPayStatusOrderByCreatedAtDesc(
            Long accountId,
            PaymentMethod paymentMethod,
            PayStatus payStatus
    );

    // =====================================================
    // COUNT
    // =====================================================
    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    // =====================================================
    // REVENUE
    // =====================================================
    @Query(value = """
        SELECT COALESCE(
            SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0))
        ,0)
        FROM orders o
        WHERE o.created_at BETWEEN :start AND :end
    """, nativeQuery = true)
    java.math.BigDecimal sumRevenueBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // =====================================================
    // PROFIT
    // =====================================================
    @Query(value = """
        SELECT COALESCE(SUM(
            (COALESCE(oi.discount_price, oi.price) - COALESCE(p.import_price,0)) * oi.quantity
        ), 0)
        FROM order_items oi
        JOIN products p ON oi.product_id = p.id
        JOIN orders o ON oi.order_id = o.id
        WHERE o.created_at BETWEEN :start AND :end
    """, nativeQuery = true)
    java.math.BigDecimal sumEstimatedProfitBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // =====================================================
    // REVENUE BY DAY
    // =====================================================
    @Query(value = """
        SELECT DATE(o.created_at) as period,
               COALESCE(SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0)),0) as revenue
        FROM orders o
        WHERE o.created_at BETWEEN :start AND :end
        GROUP BY DATE(o.created_at)
        ORDER BY DATE(o.created_at)
    """, nativeQuery = true)
    List<Object[]> revenueGroupedByDay(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // =====================================================
    // REVENUE BY MONTH
    // =====================================================
    @Query(value = """
        SELECT CONCAT(YEAR(o.created_at), '-', LPAD(MONTH(o.created_at),2,'0')) as period,
               COALESCE(SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0)),0) as revenue
        FROM orders o
        WHERE YEAR(o.created_at) = :year
        GROUP BY YEAR(o.created_at), MONTH(o.created_at)
        ORDER BY MONTH(o.created_at)
    """, nativeQuery = true)
    List<Object[]> revenueGroupedByMonth(@Param("year") int year);

    // =====================================================
    // REVENUE BY QUARTER
    // =====================================================
    @Query(value = """
        SELECT CONCAT(YEAR(o.created_at), '-Q', FLOOR((MONTH(o.created_at)-1)/3)+1) as period,
               COALESCE(SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0)),0) as revenue
        FROM orders o
        WHERE YEAR(o.created_at) = :year
        GROUP BY YEAR(o.created_at), FLOOR((MONTH(o.created_at)-1)/3)
        ORDER BY FLOOR((MONTH(o.created_at)-1)/3)
    """, nativeQuery = true)
    List<Object[]> revenueGroupedByQuarter(@Param("year") int year);

    // =====================================================
    // REVENUE BY YEAR RANGE
    // =====================================================
    @Query(value = """
        SELECT YEAR(o.created_at) as period,
               COALESCE(SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0)),0) as revenue
        FROM orders o
        WHERE YEAR(o.created_at) BETWEEN :startYear AND :endYear
        GROUP BY YEAR(o.created_at)
        ORDER BY YEAR(o.created_at)
    """, nativeQuery = true)
    List<Object[]> revenueGroupedByYearRange(
            @Param("startYear") int startYear,
            @Param("endYear") int endYear
    );
}