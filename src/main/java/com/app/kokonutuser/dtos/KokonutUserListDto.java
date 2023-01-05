package com.app.kokonutuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Woody
 * Date : 2023-01-06
 * Time :
 * Remark : 유저 리스트 조회 ListDto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KokonutUserListDto {

    private String ID; // 아이디

    private String STATE; // 상태 : "1" 정상, "2" 휴면

    private Date REGDATE; // 회원가입 일시

    private Date LAST_LOGIN_DATE; // 최근 접속 일시

}