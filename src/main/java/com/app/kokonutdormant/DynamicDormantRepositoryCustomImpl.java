package com.app.kokonutdormant;

import com.app.kokonutdormant.dtos.KokonutDormantListDto;
import com.app.kokonutuser.dtos.KokonutUserListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

}
