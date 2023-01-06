package com.app.kokonut.setting;


import com.app.kokonut.setting.dto.SettingDetailDto;

/**
 * @author Joy
 * Date : 2023-01-05
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 SettingDao, AdminSettingDao 쿼리호출
 */
public interface SettingRepositoryCustom {

    // 개인별 세선타임을 설정하기 위한 admin_setting 테이블의 사용여부가 결정되지 않아서
    // 현재(22.01.06) 해당 부분 리팩토링 진행하지 않음.
    // AdminSettingDao
//    String IsOperation(); // DB스케쥴러 작동여부 조회, 사용 안함
//    void UpdateOperation(String isOperation); // DB스케줄러 작동여부 업데이트 (Y:작동, N:미작동), 사용안함
//    Integer SelectLoginSessionTime(); // 로그인 세션타임 시간 조회
//    void UpdateLoginSessionTime(Integer loginSessionTime); // 로그인 세션타임 시간 업데이트

    // SettingDao
    /**
     * 관리자 환경 설정 상세 조회
     * AS-IS SelectSettingByCompanyIdx
     **/
    SettingDetailDto findSettingDetailByCompanyIdx(Integer companyIdx);

//    public void InsertSetting(HashMap<String, Object> paramMap); // 관리자 환경설정 등록
//
//    public void UpdateSetting(HashMap<String, Object> paramMap); // 관리자 환경설정 수정
//
//    public void DeleteByCompanyIdx(int companyIdx); // 관리자 환경설정 전체 삭제
//
}