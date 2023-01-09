package com.app.kokonutuser;

import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateListDto;
import com.app.kokonutuser.dtos.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    // 유저테이블의 회원 조회 나중에 밑에 코드로 수정하기 Dto 정해질때
//    @Override
//    public List<가져올데이터Dto> selectUserList(String searchQuery) {
//        return jdbcTemplate.query(searchQuery,
//                new BeanPropertyRowMapper<>(가져올데이터Dto.class));
//    }
    @Override
    public List<Map<String, Object>> selectUserList(String searchQuery) {
        return jdbcTemplate.queryForList(searchQuery);
    }

    // 유저테이블 회원 수 조회
    @Override
    public int selectUserListCount(String searchQuery) {
        return jdbcTemplate.queryForObject(searchQuery, Integer.class);
    }

    // 유저테이블 단일회원 조회
    @Override
    public List<KokonutUserRemoveInfoDto> selectUserDataByIdx(String searchQuery) {
        return jdbcTemplate.query(searchQuery,
                new BeanPropertyRowMapper<>(KokonutUserRemoveInfoDto.class));
    }

    // 1년전에 가입한 회원목록 조회
    @Override
    public List<KokonutOneYearAgeRegUserListDto> selectOneYearAgoRegUserListByTableName(String searchQuery) {
        return jdbcTemplate.query(searchQuery,
                new BeanPropertyRowMapper<>(KokonutOneYearAgeRegUserListDto.class));
    }

    // 유저 등록여부 조회
    @Override
    public Integer selectCount(String searchQuery) {
        return jdbcTemplate.queryForObject(searchQuery, Integer.class);
    }

    // 저장 유저의 마지막 IDX 조회
    @Override
    public Integer selectTableLastIdx(String searchQuery) {
        return jdbcTemplate.queryForObject(searchQuery, Integer.class);
    }

    // 유저테이블의 컬럼 조회
    @Override
    public List<KokonutUserFieldDto> selectColumns(String searchQuery) {
        return jdbcTemplate.query(searchQuery,
                new BeanPropertyRowMapper<>(KokonutUserFieldDto.class));
    }

    // 필드값을 통해 아이디 조회
    @Override
    public String selectIdByFieldAndValue(String searchQuery) {
        return jdbcTemplate.queryForObject(searchQuery, String.class);
    }

    // 필드-값 쌍으로 사용자 컬럼값 조회
    @Override
    public List<KokonutUserFieldInfoDto> selectFieldList(String VALUE, String searchQuery) {
        return jdbcTemplate.query(
            searchQuery,
            (rs, rowNum) ->
                new KokonutUserFieldInfoDto(
                    rs.getLong("IDX"),
                    rs.getObject(VALUE)
                )
        );
    }

    // 현재 비밀번호 값 호출
    @Override
    public List<KokonutUserPwInfoDto> findByNowPw(String searchQuery) {
        return jdbcTemplate.query(
                searchQuery,
                (rs, rowNum) ->
                        new KokonutUserPwInfoDto(
                                rs.getLong("IDX"),
                                rs.getString("PASSWORD")
                        )
        );
    }

    // 금일부터 한달전까지 속해 있는 유저수 조회
    @Override
    public Integer selectCountByThisMonth(String searchQuery) {
        return jdbcTemplate.queryForObject(searchQuery, Integer.class);
    }

//    SimpleJdbcTemplate template = new SimpleJdbcTemplate(dataSource);


    public List<KokonutUserListDto> findByUserPage(String searchQuery) {
        return jdbcTemplate.query(
            searchQuery,
            (rs, rowNum) ->
                new KokonutUserListDto(
                    rs.getLong("IDX"),
                    rs.getString("ID"),
                    rs.getTimestamp("REGDATE"),
                    rs.getTimestamp("LAST_LOGIN_DATE")
                )
        );
    }

    // 아이디 존재 유무 확인
    @Override
    public Integer selectUserIdCheck(String searchQuery) {
        return jdbcTemplate.queryForObject(searchQuery, Integer.class);
    }

}
