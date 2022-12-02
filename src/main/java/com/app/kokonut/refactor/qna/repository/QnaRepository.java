package com.app.kokonut.refactor.qna.repository;

import com.app.kokonut.refactor.qna.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QnaRepository extends JpaRepository<Qna, Integer>, JpaSpecificationExecutor<Qna> {

}