package com.app.kokonut.notice.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @Id
    @ApiModelProperty("키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * 최종 등록한 사람의 IDX를 저장
     */
    @Column(name = "ADMIN_IDX")
    @ApiModelProperty("최종 등록한 사람의 IDX를 저장")
    private Integer adminIdx;

    /**
     * 상단공지여부(0:일반,1:상단공지)
     */
    @Column(name = "IS_NOTICE")
    @ApiModelProperty("상단공지여부(0:일반,1:상단공지)")
    private Integer isNotice;

    /**
     * 제목
     */
    @Column(name = "TITLE")
    @ApiModelProperty("제목")
    private String title;

    /**
     * 내용
     */
    @ApiModelProperty("내용")
    @Column(name = "CONTENT")
    private String content;

    /**
     * 조회수(사용여부확인필요)
     */
    @Column(name = "VIEW_COUNT")
    @ApiModelProperty("조회수(사용여부확인필요)")
    private Integer viewCount;

    /**
     * 첨부파일 아이디
     */
    @ApiModelProperty("첨부파일 아이디")
    @Column(name = "FILE_GROUP_ID")
    private String fileGroupId;

    /**
     * 작성정보 작성자
     */
    @ApiModelProperty("작성정보 작성자")
    @Column(name = "REGISTER_NAME")
    private String registerName;

    /**
     * 게시일자
     */
    @ApiModelProperty("게시일자")
    @Column(name = "REGIST_DATE", nullable = false)
    private LocalDateTime registDate;

    /**
     * 등록일자
     */
    @Column(name = "REGDATE")
    @ApiModelProperty("등록일자")
    private LocalDateTime regdate;

    /**
     * 수정자
     */
    @ApiModelProperty("수정자")
    @Column(name = "MODIFIER_IDX")
    private Integer modifierIdx;

    /**
     * 수정정보 수정자
     */
    @ApiModelProperty("수정정보 수정자")
    @Column(name = "MODIFIER_NAME")
    private String modifierName;

    /**
     * 수정일자
     */
    @ApiModelProperty("수정일자")
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    /**
     * 0:게시중지,1:게시중,2:게시대기
     */
    @Column(name = "STATE")
    @ApiModelProperty("0:게시중지,1:게시중,2:게시대기")
    private Integer state;

    /**
     * 게시중지 일자
     */
    @Column(name = "STOP_DATE")
    @ApiModelProperty("게시중지 일자")
    private LocalDateTime stopDate;

}
