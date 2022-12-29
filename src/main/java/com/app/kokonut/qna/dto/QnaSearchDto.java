package com.app.kokonut.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Joy
 * Date : 2022-12-28
 * Time :
 * Remark : Qna 조회용 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnaSearchDto {

    private Integer idx;

    private Integer state;  // 상태(0:답변대기,1:답변완료)

    private Integer type;   // 분류(0:기타,1:회원정보,2:사업자정보,3:Kokonut서비스,4:결제)

    private Integer adminIdx;

    private LocalDateTime stimeStart; // 시작 날짜

    private LocalDateTime stimeEnd; // 끝 날짜

    private String searchText;  // 검색어

}
