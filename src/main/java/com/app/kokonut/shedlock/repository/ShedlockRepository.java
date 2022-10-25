package com.app.kokonut.shedlock.repository;

import com.app.kokonut.shedlock.entity.Shedlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShedlockRepository extends JpaRepository<Shedlock, String>, JpaSpecificationExecutor<Shedlock> {

}