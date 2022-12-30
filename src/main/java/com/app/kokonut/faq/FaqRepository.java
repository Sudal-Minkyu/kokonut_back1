package com.app.kokonut.faq;

import com.app.kokonut.faq.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FaqRepository extends JpaRepository<Faq, Integer>, JpaSpecificationExecutor<Faq>, FaqRepositoryCustom {

}