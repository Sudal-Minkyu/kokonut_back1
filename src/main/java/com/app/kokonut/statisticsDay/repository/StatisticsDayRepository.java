package com.app.kokonut.statisticsDay.repository;

import com.app.kokonut.statisticsDay.entity.StatisticsDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StatisticsDayRepository extends JpaRepository<StatisticsDay, Integer>, JpaSpecificationExecutor<StatisticsDay> {

}