package com.spring.postgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.postgres.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
