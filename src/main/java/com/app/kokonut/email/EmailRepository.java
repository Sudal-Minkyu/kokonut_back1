package com.app.kokonut.email;

import com.app.kokonut.email.dto.EmailDto;
import com.app.kokonut.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmailRepository extends JpaRepository<Email, Integer>, JpaSpecificationExecutor<Email>, EmailRepositoryCustom {
    //이메일 인덱스로 이메일 내용 조회하기
    @Query("select a.idx, a.receiverType, a.receiverAdminIdxList, a.emailGroupIdx, a.title, a.contents, a.regdate from Email a where a.idx = :idx")
    EmailDto findEmailByIdx(int idx);
}