package com.app.kokonut.email.email;

import com.app.kokonut.email.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface EmailRepository extends JpaRepository<Email, Integer>, JpaSpecificationExecutor<Email>, EmailRepositoryCustom {

}