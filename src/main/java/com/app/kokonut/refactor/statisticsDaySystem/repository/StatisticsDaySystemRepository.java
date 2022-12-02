package com.app.kokonut.refactor.statisticsDaySystem.repository;

import com.app.kokonut.refactor.statisticsDaySystem.entity.StatisticsDaySystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatisticsDaySystemRepository extends JpaRepository<StatisticsDaySystem, Integer>, JpaSpecificationExecutor<StatisticsDaySystem> {

}