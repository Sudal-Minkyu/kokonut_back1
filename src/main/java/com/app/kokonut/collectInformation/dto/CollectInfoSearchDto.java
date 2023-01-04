package com.app.kokonut.collectInformation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Joy
 * Date : 2023-01-94
 * Time :
 * Remark : 개인정보 처리방침 - 개인정보 수집 및 이용 안내 목록 검색을 위한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectInfoSearchDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String searchText;  // 검색어
}
