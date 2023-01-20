package com.app.kokonut.qna.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Joy
 * Date : 2022-12-28
 * Time :
 * Remark : 1:1 문의글 등록 Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class QnaQuestionSaveDto {

    @ApiModelProperty("키")
    private Integer idx;

    @ApiModelProperty("질문자(사용자 키)")
    private Long adminId;

    @ApiModelProperty("제목")
    private String title;

    @ApiModelProperty("문의내용")
    private String content;

    @ApiModelProperty("첨부파일 목록")
    private List<MultipartFile> multipartFiles; // AS-IS fileGroupId

    @ApiModelProperty("분류(0:기타,1:회원정보,2:사업자정보,3:Kokonut서비스,4:결제)")
    private Integer type;

    @ApiModelProperty("질문등록일시")
    private LocalDateTime regdate;

    // Admin Table
    @ApiModelProperty("질문자 이메일")
    private String email;

}
