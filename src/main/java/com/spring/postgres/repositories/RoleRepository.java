package com.spring.postgres.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.postgres.models.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {

}
