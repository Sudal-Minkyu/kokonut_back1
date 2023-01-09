package com.app.kokonut.keydata;

import com.app.kokonut.keydata.dtos.KeyDataDto;
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

    // AWS S3 keyData 조회
    @Override
    public String findByAWSKey(String keyName) {

        QKeyData keyData = QKeyData.keyData;

        JPQLQuery<String> query = from(keyData)
                .where(keyData.keyGroup.eq("aws_s3").and(keyData.keyName.eq(keyName)))
                .select(Projections.constructor(String.class,
                        keyData.keyValue
                ));

        return query.fetchOne();
    }

    // KMS keyData 조회
    @Override
    public String findByKMSKey(String keyName) {

        QKeyData keyData = QKeyData.keyData;

        JPQLQuery<String> query = from(keyData)
                .where(keyData.keyGroup.eq("kms").and(keyData.keyName.eq(keyName)))
                .select(Projections.constructor(String.class,
                        keyData.keyValue
                ));

        return query.fetchOne();
    }

    // NCLOUD keyData 조회
    @Override
    public String findByNCLOUDKey(String keyName) {

        QKeyData keyData = QKeyData.keyData;

        JPQLQuery<String> query = from(keyData)
                .where(keyData.keyGroup.eq("ncloud").and(keyData.keyName.eq(keyName)))
                .select(Projections.constructor(String.class,
                        keyData.keyValue
                ));

        return query.fetchOne();
    }

    // NICE keyData 조회
    @Override
    public String findByNICEKey(String keyName) {

        QKeyData keyData = QKeyData.keyData;

        JPQLQuery<String> query = from(keyData)
                .where(keyData.keyGroup.eq("nice").and(keyData.keyName.eq(keyName)))
                .select(Projections.constructor(String.class,
                        keyData.keyValue
                ));

        return query.fetchOne();
    }

}
