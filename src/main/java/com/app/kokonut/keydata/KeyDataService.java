package com.app.kokonut.keydata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Woody
 * Date : 2023-01-04
 * Time :
 * Remark :
 */
@Slf4j
@Service
public class KeyDataService {

    private final KeyDataDataRepository keyDataRepository;

    @Autowired
    public KeyDataService(KeyDataDataRepository keyDataRepository) {
        this.keyDataRepository = keyDataRepository;
    }

    // 키값 조회
    public String findByKeyValue(String keyName) {
//        log.info("findByKeyValue 호출 : "+keyName);

        KeyDataDto keyValue = keyDataRepository.findByKeyValue(keyName);
//        log.info("가져온 값 : "+keyValue);

        if(keyValue != null) {
            return keyValue.getKeyValue();
        } else {
            log.error("findByKeyValue 호출 - 결과값이 존재하지 않습니다. keyName : "+keyName+" / result : "+null);

            return null;
        }
    }

}
