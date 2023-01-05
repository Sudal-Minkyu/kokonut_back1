package com.app.kokonut.keydata;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2023-01-04
 * Time :
 * Remark :
 */
@Repository
public class KeyDataRepositoryCustomImpl extends QuerydslRepositorySupport implements KeyDataRepositoryCustom {

    public KeyDataRepositoryCustomImpl() {
        super(KeyData.class);
    }

    // keyValue 조회
    @Override
    public KeyDataDto findByKeyValue(String keyName) {

        QKeyData keyData = QKeyData.keyData;

        JPQLQuery<KeyDataDto> query = from(keyData)
                .where(keyData.keyName.eq(keyName))
                .select(Projections.constructor(KeyDataDto.class,
                        keyData.keyValue
                ));

        return query.fetchOne();
    }

}
