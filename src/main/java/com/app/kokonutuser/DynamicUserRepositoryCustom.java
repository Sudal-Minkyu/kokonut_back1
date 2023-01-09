package com.app.kokonutuser;

import com.app.kokonutuser.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DynamicUserRepositoryCustom {

    void userCommonTable(String commonQuery); // 생성, 삭제, 업데이트 실행 공용

    int selectExistTable(String businessNumber); // 보내는 값 - tableName = businessNumber

    List<Map<String, Object>> selectUserList(String searchQuery); // kokonut_user 회원리스트 조회

    int selectUserListCount(String businessNumber); // kokonut_user 회원수 조회

    List<KokonutUserRemoveInfoDto> selectUserDataByIdx(String searchQuery); // kokonut_user 단일회원 조회

    List<KokonutOneYearAgeRegUserListDto> selectOneYearAgoRegUserListByTableName(String searchQuery); // 1년전에 가입한 회원목록 조회

    Integer selectCount(String searchQuery); // 회원 등록여부 조회

    Integer selectTableLastIdx(String searchQuery); // 저장 유저의 마지막 IDX 조회

    List<KokonutUserFieldDto> selectColumns(String searchQuery); // 유저테이블의 컬럼 조회

    String selectIdByFieldAndValue(String searchQuery); // 필드값을 통해 아이디 조회

    List<KokonutUserFieldInfoDto> selectFieldList(String VALUE, String searchQuery); // 필드-값 쌍으로 사용자 컬럼값 조회

    List<KokonutUserPwInfoDto> findByNowPw(String searchQuery); // 현재 비밀번호 값 호출

    Integer selectCountByThisMonth(String searchQuery); // 금일부터 한달전까지 속해 있는 유저수 조회

    List<KokonutUserListDto> findByUserPage(String searchQuery); // 유저 리스트 조회

    Integer selectUserIdCheck(String searchQuery); // 아이디 존재 유무 확인
}
