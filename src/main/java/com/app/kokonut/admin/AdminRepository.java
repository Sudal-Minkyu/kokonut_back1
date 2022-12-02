package com.app.kokonut.admin;

import com.app.kokonut.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>, JpaSpecificationExecutor<Admin>, AdminRepositoryCustom {
    Optional<Admin> findByEmail(String email);
    boolean existsByEmail(String email);
}