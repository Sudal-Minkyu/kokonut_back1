package com.app.kokonutuser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-27
 * Time :
 * Remark : Kokonut-user DB & 테이블 - 쿼리문 선언부 모두 NativeQuery로 실행한다.
 */
@Slf4j
@Repository
public class DynamicUserRepositoryCustomImpl implements DynamicUserRepositoryCustom {
//public class DynamicUserRepositoryCustomImpl extends QuerydslRepositorySupport implements DynamicUserRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DynamicUserRepositoryCustomImpl(@Qualifier("kokonutUserJdbcTemplate") JdbcTemplate jdbcTemplate) {
//        super(Object.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    // 유저테이블 중복 체크 메서드
    @Override
    public int selectExistTable(String businessNumber) {
        String sql = "SELECT EXISTS (SELECT 1 FROM Information_schema.tables WHERE table_name = "+"'"+businessNumber+"'"+") AS flag";
//        log.info("중복체크 sql : "+sql);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // 유저테이블 생성/삭제/업데이트 메서드
    @Override
    public void userCommonTable(String commonQuery) {

//        EntityManager em = getEntityManager();
//        StringBuilder sb = new StringBuilder();

        // 네이티브 쿼리문
//        sb.append("use kokonut_user; ");
//        sb.append(createQuery);

        jdbcTemplate.execute(commonQuery);
//        return entityManager.createNativeQuery(sb.toString());

        // 쿼리조건 선언부
//        Query query = entityManager.createNativeQuery(sb.toString());
//
//        return 1;

    }

    // 유저테이블의 회원 조회
    @Override
    public List<Map<String, Object>> selectUserList(String searchQuery) {
        return jdbcTemplate.queryForList(searchQuery);
    }

    // 유저테이블 회원 수 조회
    @Override
    public int selectUserListCount(String searchQuery) {
        return jdbcTemplate.queryForObject(searchQuery, Integer.class);
    }

}
