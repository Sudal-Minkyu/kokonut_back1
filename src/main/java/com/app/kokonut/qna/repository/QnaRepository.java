package com.app.kokonut.qna.repository;

import com.app.kokonut.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QnaRepository extends JpaRepository<Qna, Integer>, JpaSpecificationExecutor<Qna> {

}