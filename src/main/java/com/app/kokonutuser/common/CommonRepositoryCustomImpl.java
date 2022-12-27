package com.app.kokonutuser.common;

import com.app.kokonutuser.common.dto.CommonFieldDto;
import lombok.extern.slf4j.Slf4j;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Woody
 * Date : 2022-12-27
 * Time :
 * Remark : Kokonut-user DB, 테이블 쿼리문 선언부
 */
@Slf4j
@Repository
public class CommonRepositoryCustomImpl extends QuerydslRepositorySupport implements CommonRepositoryCustom {

    @Autowired
    JpaResultMapper jpaResultMapper;

    public CommonRepositoryCustomImpl() {
        super(Object.class);
    }

    public List<CommonFieldDto> selectCommonUserTable() {

        EntityManager em = getEntityManager();
        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
        sb.append("SHOW FULL COLUMNS FROM kokonut_user.common; \n");

        // 쿼리조건 선언부
        Query query = em.createNativeQuery(sb.toString());

        return jpaResultMapper.list(query, CommonFieldDto.class);
    }

}
