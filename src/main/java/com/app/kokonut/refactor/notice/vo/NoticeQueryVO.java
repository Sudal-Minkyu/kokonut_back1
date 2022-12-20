package com.app.kokonut.refactor.notice.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("Retrieve by query ")
public class NoticeQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 키
     */
    @ApiModelProperty("키")
    private Integer idx;


    /**
     * 최종 등록한 사람의 IDX를 저장
     */
    @ApiModelProperty("최종 등록한 사람의 IDX를 저장")
    private Integer adminIdx;


    /**
     * 상단공지여부(0:일반,1:상단공지)
     */
    @ApiModelProperty("상단공지여부(0:일반,1:상단공지)")
    private Integer notice;


    /**
     * 제목
     */
    @ApiModelProperty("제목")
    private String title;


    /**
     * 내용
     */
    @ApiModelProperty("내용")
    private String content;


    /**
     * 조회수(사용여부확인필요)
     */
    @ApiModelProperty("조회수(사용여부확인필요)")
    private Integer viewCount;


    /**
     * 첨부파일 아이디
     */
    @ApiModelProperty("첨부파일 아이디")
    private String fileGroupId;


    /**
     * 작성정보 작성자
     */
    @ApiModelProperty("작성정보 작성자")
    private String registerName;


    /**
     * 게시일자
     */
    @ApiModelProperty("게시일자")
    private Date registDate;


    /**
     * 등록일자
     */
    @ApiModelProperty("등록일자")
    private Date regdate;


    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    private Integer modifierIdx;


    /**
     * 수정정보 수정자
     */
    @ApiModelProperty("수정정보 수정자")
    private String modifierName;


    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    private Date modifyDate;


    /**
     * 0:게시중지,1:게시중,2:게시대기
     */
    @ApiModelProperty("0:게시중지,1:게시중,2:게시대기")
    private Integer state;


    /**
     * 게시중지 일자
     */
    @ApiModelProperty("게시중지 일자")
    private Date stopDate;

}