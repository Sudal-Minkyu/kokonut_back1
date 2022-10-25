package com.app.kokonut.awsKmsHistory.repository;

import com.app.kokonut.awsKmsHistory.entity.AwsKmsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AwsKmsHistoryRepository extends JpaRepository<AwsKmsHistory, Integer>, JpaSpecificationExecutor<AwsKmsHistory> {

}