package com.app.kokonut.keydata;

import com.app.kokonut.keydata.dtos.KeyDataDto;

/**
 * @author Woody
 * Date : 2023-01-04
 * Time :
 * Remark :
 */
public interface KeyDataRepositoryCustom {

    KeyDataDto findByKeyValue(String keyName);

    String findByAWSKey(String keyName); // AWS S3 keyData 조회

    String findByKMSKey(String keyName); // KMS keyData 조회

    String findByNCLOUDKey(String keyName); // NCLOUD keyData 조회

    String findByNICEKey(String keyName); // NICE keyData 조회


}