package com.backend.pishop.repository;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.pishop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
