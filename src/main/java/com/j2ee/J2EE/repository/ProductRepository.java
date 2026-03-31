package com.j2ee.J2EE.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.j2ee.J2EE.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}