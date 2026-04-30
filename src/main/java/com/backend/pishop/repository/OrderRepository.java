package com.backend.pishop.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.pishop.entity.Order;
import com.backend.pishop.enums.OrderStatus;
import com.backend.pishop.enums.PayStatus;

public interface OrderRepository extends JpaRepository<Order, Long>{
	 @Query("""
		        SELECT o
		        FROM Order o
		        WHERE
		            (:keyword IS NULL
		                OR LOWER(o.toName) LIKE LOWER(CONCAT('%', :keyword, '%'))
		                OR LOWER(o.toPhone) LIKE LOWER(CONCAT('%', :keyword, '%'))
		                OR LOWER(o.trackingNumber) LIKE LOWER(CONCAT('%', :keyword, '%')))
		        AND (:status IS NULL OR o.orderStatus = :status)
		        AND (:accountId IS NULL OR o.account.id = :accountId)
		        ORDER BY o.createAt DESC
		    """)
		    List<Order> searchOrders(
		            @Param("keyword") String keyword,
		            @Param("status") OrderStatus status,
		            @Param("accountId") Long accountId
		    );
    List<Order> findByAccountIdOrderByCreateAtDesc(Long accountId);
    List<Order> findByOrderStatusOrderByCreateAtDesc(OrderStatus status);
    List<Order> findByAccountIdAndPaymentMethodAndPayStatusOrderByCreateAtDesc(Long accountId, String paymentMethod, PayStatus payStatus);

    // Count orders between datetimes
    long countByCreateAtBetween(LocalDateTime start, LocalDateTime end);

    // Sum revenue between dates (total_amount - discount_amount + ship_fee)
    @Query(value = "SELECT COALESCE(SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0)),0) FROM orders o WHERE o.create_at BETWEEN :start AND :end", nativeQuery = true)
    java.math.BigDecimal sumRevenueBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // Estimated profit between dates: sum( (price_paid - import_price) * quantity )
    @Query(value = "SELECT COALESCE(SUM( (COALESCE(oi.discount_price, oi.price) - COALESCE(p.import_price,0)) * oi.quantity ), 0) " +
            "FROM order_items oi " +
            "JOIN products p ON oi.product_id = p.id " +
            "JOIN orders o ON oi.order_id = o.id " +
            "WHERE o.create_at BETWEEN :start AND :end", nativeQuery = true)
    java.math.BigDecimal sumEstimatedProfitBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // Revenue grouped by day
    @Query(value = "SELECT DATE(o.create_at) as period, COALESCE(SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0)),0) as revenue " +
            "FROM orders o " +
            "WHERE o.create_at BETWEEN :start AND :end " +
            "GROUP BY DATE(o.create_at) " +
            "ORDER BY DATE(o.create_at)", nativeQuery = true)
    List<Object[]> revenueGroupedByDay(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // Revenue grouped by month in a year
    @Query(value = "SELECT CONCAT(YEAR(o.create_at), '-', LPAD(MONTH(o.create_at),2,'0')) as period, COALESCE(SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0)),0) as revenue " +
            "FROM orders o " +
            "WHERE YEAR(o.create_at) = :year " +
            "GROUP BY YEAR(o.create_at), MONTH(o.create_at) " +
            "ORDER BY MONTH(o.create_at)", nativeQuery = true)
    List<Object[]> revenueGroupedByMonth(@Param("year") int year);

    // Revenue grouped by quarter in a year
    @Query(value = "SELECT CONCAT(YEAR(o.create_at), '-Q', FLOOR((MONTH(o.create_at)-1)/3)+1) as period, COALESCE(SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0)),0) as revenue " +
            "FROM orders o " +
            "WHERE YEAR(o.create_at) = :year " +
            "GROUP BY YEAR(o.create_at), FLOOR((MONTH(o.create_at)-1)/3) " +
            "ORDER BY FLOOR((MONTH(o.create_at)-1)/3)", nativeQuery = true)
    List<Object[]> revenueGroupedByQuarter(@Param("year") int year);

    // Revenue grouped by year range
    @Query(value = "SELECT YEAR(o.create_at) as period, COALESCE(SUM(o.total_amount - COALESCE(o.discount_amount,0) + COALESCE(o.ship_fee,0)),0) as revenue " +
            "FROM orders o " +
            "WHERE YEAR(o.create_at) BETWEEN :startYear AND :endYear " +
            "GROUP BY YEAR(o.create_at) " +
            "ORDER BY YEAR(o.create_at)", nativeQuery = true)
    List<Object[]> revenueGroupedByYearRange(@Param("startYear") int startYear, @Param("endYear") int endYear);
}