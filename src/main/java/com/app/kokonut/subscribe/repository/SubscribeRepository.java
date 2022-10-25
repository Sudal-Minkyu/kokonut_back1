package com.app.kokonut.subscribe.repository;

import com.app.kokonut.subscribe.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer>, JpaSpecificationExecutor<Subscribe> {

}