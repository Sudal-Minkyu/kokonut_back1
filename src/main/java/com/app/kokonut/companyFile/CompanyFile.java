package com.app.kokonut.companyFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(of = "idx")
@Data
@NoArgsConstructor
@Table(name="company_file")
public class CompanyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @ApiModelProperty("기업 IDX")
    @Column(name = "COMPANY_IDX")
    private Integer companyIdx;

    @ApiModelProperty("S3 파일 경로")
    @Column(name = "CF_PATH")
    private String cfPath;

    @ApiModelProperty("S3 파일 명")
    @Column(name = "CF_FILENAME")
    private String cfFilename;

    @ApiModelProperty("원래 파일명")
    @Column(name = "CF_ORIGINAL_FILENAME")
    private String cfOriginalFilename;

    @ApiModelProperty("용량")
    @Column(name = "CF_VOLUME")
    private Long cfVolume;

    @ApiModelProperty("최초생성자 IDX")
    @Column(name = "REGIDX")
    private Integer regIdx;

    @ApiModelProperty("최초생성자 날짜")
    @Column(name = "REGDATE")
    private LocalDateTime regDate;

    @ApiModelProperty("수정자 IDX")
    @Column(name = "MODIFYIDX")
    private Integer modifyIdx;

    @ApiModelProperty("수정 날짜")
    @Column(name = "MODIFYDATE")
    private LocalDateTime modifyDate;

}
