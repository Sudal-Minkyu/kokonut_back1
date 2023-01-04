package com.app.kokonut.keydata;

/**
 * @author Woody
 * Date : 2023-01-04
 * Time :
 * Remark :
 */
public interface KeyDataRepositoryCustom {

    KeyDataDto findByKeyValue(String keyName);

}