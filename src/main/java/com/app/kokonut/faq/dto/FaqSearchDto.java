package com.app.kokonut.faq.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("")
public class FaqSearchDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer state;  // 상태(0:게시중지,1:게시중,2:게시대기)

    private Integer type;   // 분류(0:기타,1:회원정보,2:사업자정보,3:kokonut서비스,4:결제)

    private LocalDateTime stimeStart; // 시작 날짜

    private LocalDateTime stimeEnd; // 끝 날짜

    private String searchText;  // 검색어

}
