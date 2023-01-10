package com.app.kokonutdormant;

import com.app.kokonutdormant.dtos.KokonutDormantListDto;
import com.app.kokonutuser.dtos.KokonutRemoveInfoDto;
import com.app.kokonutuser.dtos.KokonutUserFieldDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Woody
 * Date : 2023-01-03
 * Time :
 * Remark : Kokonut-dormant DB & 테이블 - 쿼리문 선언부 모두 NativeQuery로 실행한다.
 */
@Slf4j
@Repository
public class DynamicDormantRepositoryCustomImpl implements DynamicDormantRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DynamicDormantRepositoryCustomImpl(@Qualifier("kokonutDormantJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 삭제테이블 생성/삭제/업데이트 메서드
    @Override
    public void dormantCommonTable(String commonQuery) {
        jdbcTemplate.execute(commonQuery);
    }

    public List<KokonutDormantListDto> findByDormantPage(String searchQuery) {
        return jdbcTemplate.query(
            searchQuery,
            (rs, rowNum) ->
                new KokonutDormantListDto(
                    rs.getLong("IDX"),
                    rs.getString("ID"),
                    rs.getTimestamp("REGDATE"),
                    rs.getTimestamp("LAST_LOGIN_DATE")
                )
        );
    }

    // 유저 등록여부 조회
    @Override
    public Integer selectDormantCount(String searchQuery) {
        return jdbcTemplate.queryForObject(searchQuery, Integer.class);
    }

    // 삭제할 휴면테이블 단일회원 조회
    @Override
    public List<KokonutRemoveInfoDto> selectDormantDataByIdx(String searchQuery) {
        return jdbcTemplate.query(searchQuery,
                new BeanPropertyRowMapper<>(KokonutRemoveInfoDto.class));
    }

    // 아이디 존재 유무 확인
    @Override
    public Integer selectDormantIdCheck(String searchQuery) {
        return jdbcTemplate.queryForObject(searchQuery, Integer.class);
    }

    // 휴면테이블의 컬럼 조회
    @Override
    public List<KokonutUserFieldDto> selectDormantColumns(String searchQuery) {
        return jdbcTemplate.query(searchQuery,
                new BeanPropertyRowMapper<>(KokonutUserFieldDto.class));
    }


}
