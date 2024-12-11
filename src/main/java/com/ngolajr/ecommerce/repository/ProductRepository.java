package com.ngolajr.ecommerce.repository;

import com.ngolajr.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryContainsIgnoreCase(String category);
    List<Product> findByNameContainsIgnoreCase(String name);
    Optional<Product> findByNameEqualsIgnoreCase(String name);
}
