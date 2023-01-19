package com.app.kokonut.qna;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(of = "qnaId")
@Data
@NoArgsConstructor
@Table(name="kn_qna")
public class Qna implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @Id
    @ApiModelProperty("키")
    @Column(name = "qna_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qnaId;

    /**
     * 회사(Company) 키
     */
    @Column(name = "company_id")
    @ApiModelProperty("회사(Company) 키")
    private Long companyId;

    /**
     * 등록자
     */
    @ApiModelProperty("등록자")
    @Column(name = "admin_id")
    private Long adminId;

    /**
     * 제목
     */
    @Column(name = "qna_title")
    @ApiModelProperty("제목")
    private String qnaTitle;

    /**
     * 문의내용
     */
    @Column(name = "qna_content")
    @ApiModelProperty("문의내용")
    private String qnaContent;

    /**
     * 분류(0:기타,1:회원정보,2:사업자정보,3:Kokonut서비스,4:결제)
     */
    @Column(name = "qna_type")
    @ApiModelProperty("분류(0:기타,1:회원정보,2:사업자정보,3:Kokonut서비스,4:결제)")
    private Integer qnaType;

    /**
     * 상태(0:답변대기,1:답변완료)
     */
    @Column(name = "qna_state")
    @ApiModelProperty("상태(0:답변대기,1:답변완료)")
    private Integer qnaState;

    /**
     * 답변 내용
     */
    @Column(name = "qna_answer")
    @ApiModelProperty("답변 내용")
    private String qnaAnswer;

    /**
     * 등록자 email
     */
    @ApiModelProperty("등록자 email")
    @Column(name = "insert_email", nullable = false)
    private String insert_email;

    /**
     * 등록 날짜
     */
    @ApiModelProperty("등록 날짜")
    @Column(name = "insert_date", nullable = false)
    private LocalDateTime insert_date;

    /**
     * 수정자
     */
    @ApiModelProperty("수정자 id")
    @Column(name = "modify_id")
    private Long modify_id;

    /**
     * 수정자 이름
     */
    @ApiModelProperty("수정자 email")
    @Column(name = "modify_email")
    private String modify_email;

    /**
     * 수정 날짜
     */
    @ApiModelProperty("수정 날짜")
    @Column(name = "modify_date")
    private LocalDateTime modify_date;

}
