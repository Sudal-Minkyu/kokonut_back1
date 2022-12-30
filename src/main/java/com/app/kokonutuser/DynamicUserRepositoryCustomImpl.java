package com.app.kokonutuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2022-12-27
 * Time :
 * Remark : Kokonut-user DB & 테이블 - 쿼리문 선언부 모두 NativeQuery로 실행한다.
 */
@Repository
public class DynamicUserRepositoryCustomImpl implements DynamicUserRepositoryCustom {
//public class DynamicUserRepositoryCustomImpl extends QuerydslRepositorySupport implements DynamicUserRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DynamicUserRepositoryCustomImpl(@Qualifier("kokonutUserJdbcTemplate") JdbcTemplate jdbcTemplate) {
//        super(Object.class);
        this.jdbcTemplate = jdbcTemplate;
    }


    // 유저테이블 생성 메서드
    @Override
    public void userCreateTable(String createQuery) {

//        EntityManager em = getEntityManager();
//        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
//        sb.append("use kokonut_user; ");
//        sb.append(createQuery);

        jdbcTemplate.execute(createQuery);
//        return entityManager.createNativeQuery(sb.toString());

        // 쿼리조건 선언부
//        Query query = entityManager.createNativeQuery(sb.toString());
//
//        return 1;

    }



}
