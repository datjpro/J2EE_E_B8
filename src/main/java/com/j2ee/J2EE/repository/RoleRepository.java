package com.j2ee.J2EE.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ee.J2EE.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}