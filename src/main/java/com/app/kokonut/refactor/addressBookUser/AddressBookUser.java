package com.app.kokonut.refactor.addressBookUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EqualsAndHashCode(of = "idx")
@Data
@NoArgsConstructor
@Table(name="address_book_user")
public class AddressBookUser implements Serializable {

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
     * 주소록 키
     */
    @ApiModelProperty("주소록 키")
    @Column(name = "ADDRESS_BOOK_IDX")
    private Integer addressBookIdx;

    /**
     * 아이디
     */
    @Column(name = "ID")
    @ApiModelProperty("아이디")
    private String id;

    /**
     * 등록일시
     */
    @ApiModelProperty("등록일시")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

}
