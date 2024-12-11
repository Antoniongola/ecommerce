package com.ngolajr.ecommerce.repository;
import com.ngolajr.ecommerce.model.Role;
import com.ngolajr.ecommerce.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByRolesContaining(Role role, Pageable pageable);
    Optional<User> findByUsername(String username);
    Page<User> findAll(Pageable pageable);

}
