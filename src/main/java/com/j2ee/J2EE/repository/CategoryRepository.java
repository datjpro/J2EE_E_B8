package com.j2ee.J2EE.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ee.J2EE.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
