package com.app.kokonut.refactor.shedlock.repository;

import com.app.kokonut.refactor.shedlock.entity.Shedlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShedlockRepository extends JpaRepository<Shedlock, String>, JpaSpecificationExecutor<Shedlock> {

}