package com.backend.pishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.pishop.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
