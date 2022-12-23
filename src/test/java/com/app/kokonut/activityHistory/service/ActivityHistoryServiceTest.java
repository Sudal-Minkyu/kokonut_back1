package com.app.kokonut.activityHistory.service;

import com.app.kokonut.activity.Activity;
import com.app.kokonut.activity.ActivityRepository;
import com.app.kokonut.activityHistory.ActivityHistory;
import com.app.kokonut.activityHistory.ActivityHistoryRepository;
import com.app.kokonut.activityHistory.ActivityHistoryService;
import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.company.Company;
import com.app.kokonut.company.CompanyRepository;
import com.app.kokonut.refactor.adminLevel.AdminLevelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

@AutoConfigureMockMvc
@SpringBootTest
class ActivityHistoryServiceTest {

    @Autowired
    private ActivityHistoryService activityHistoryService;

    @Autowired
    private ActivityHistoryRepository activityHistoryRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AdminLevelRepository adminLevelRepository;

//    EntityManager entityManager;

    // 테스트하기 사전데이터 넣기
    @BeforeEach
    void testDataInsert(){

        // https://devbksheen.tistory.com/entry/H2-Database-%EC%82%AC%EC%9A%A9%EC%9E%90-%EC%A0%95%EC%9D%98-%ED%95%A8%EC%88%98-%EB%A7%8C%EB%93%A4%EA%B8%B0
        // 이게 왜 안될까..
//        entityManager
//                .createNativeQuery("CREATE ALIAS IF NOT EXISTS DATE_FORMAT FOR \"com.app.kokonut.H2CustomAlias.date_format\"")
//                .executeUpdate();

        java.sql.Date systemDate = new Date(System.currentTimeMillis());
        System.out.println("현재 날짜 : "+systemDate);

        Company company = new Company();
        company.setCompanyName("기업명");
        company.setRegdate(systemDate);
        Company saveCompany = companyRepository.save(company);
        System.out.println("저장된 saveCompany : "+saveCompany);

        Admin admin = new Admin();
        admin.setCompanyIdx(saveCompany.getIdx());
        admin.setName("사용자");
        Admin saveAdmin = adminRepository.save(admin);
        System.out.println("저장된 saveAdmin : "+saveAdmin);

        Activity activity = new Activity();
        activity.setType(1);
        activity.setRegdate(systemDate);
        activity.setModifyDate(systemDate);
        Activity saveActivity = activityRepository.save(activity);
        System.out.println("저장된 saveAdmin : "+saveAdmin);


        // given
        int adminIdx = saveAdmin.getIdx();
        int companyIdx = saveCompany.getIdx();
        int activityIdx = saveActivity.getIdx();

        ActivityHistory activityHistory = new ActivityHistory();

        activityHistory.setAdminIdx(adminIdx);
        activityHistory.setCompanyIdx(companyIdx);
        activityHistory.setActivityIdx(activityIdx);
        activityHistory.setType(2);
        activityHistory.setRegdate(systemDate);

        activityHistoryRepository.save(activityHistory);

    }

    // 사전데이터 삭제
    @AfterEach
    void testDataDelete(){
        adminRepository.deleteAll();
        activityHistoryRepository.deleteAll();
        adminLevelRepository.deleteAll();
        activityRepository.deleteAll();
    }

//    @Test
//    @DisplayName("활동내역 Column 리스트 조회 테스트")
//    public void findByActivityHistoryColumnListTest(){
//        // when
//        List<Column> columnList = activityHistoryService.findByActivityHistoryColumnList();
//        System.out.println("columnList : "+ columnList);
//    }

//    @Test
//    @DisplayName("활동내역 리스트 조회 테스트1 : 타입이 4일 경우 ->  type In 2,3")
//    public void findByActivityHistoryListTest1(){
//
//        // given
//        ActivityHistorySearchDto activityHistorySearchDto = new ActivityHistorySearchDto();
//        activityHistorySearchDto.setType(4);
//
//        // when
//        List<ActivityHistoryListDto> activityHistoryListDtos = activityHistoryService.findByActivityHistoryList(activityHistorySearchDto);
//        System.out.println("activityHistoryListDtos : "+ activityHistoryListDtos);
//    }

//    @Test
//    @DisplayName("활동내역 리스트 조회 테스트2 : 타입이 1일경우 type = 타입")
//    public void findByActivityHistoryListTest2(){
//
//        // given
//        ActivityHistorySearchDto activityHistorySearchDto = new ActivityHistorySearchDto();
//        activityHistorySearchDto.setType(1);
//
//        // when
//        List<ActivityHistoryListDto> activityHistoryListDtos = activityHistoryService.findByActivityHistoryList(activityHistorySearchDto);
//        System.out.println("activityHistoryListDtos : "+ activityHistoryListDtos);
//    }
//
//    @Test
//    @DisplayName("활동내역 정보 리스트 조회 테스트 : type 4 일 경우")
//    public void findByActivityHistoryByCompanyIdxAndTypeListTest1(){
//
//        // given
//        Integer companyIdx = 1;
//        Integer type = 4;
//
//        // when
//        List<ActivityHistoryInfoListDto> activityHistoryInfoListDtos = activityHistoryService.findByActivityHistoryByCompanyIdxAndTypeList(companyIdx, type);
//        System.out.println("activityHistoryInfoListDtos : "+ activityHistoryInfoListDtos);
//    }

//    @Test
//    @DisplayName("활동내역 정보 리스트 조회 테스트 : type 2 경우")
//    public void findByActivityHistoryByCompanyIdxAndTypeListTest2(){
//
//        // given
//        Integer companyIdx = 1;
//        Integer type = 2;
//
//        // when
//        List<ActivityHistoryInfoListDto> activityHistoryInfoListDtos = activityHistoryService.findByActivityHistoryByCompanyIdxAndTypeList(companyIdx, type);
//        System.out.println("activityHistoryInfoListDtos : "+ activityHistoryInfoListDtos);
//    }

//    @Test
//    @DisplayName("활동내역 상세보기 테스트")
//    public void findByActivityHistoryByIdxTest(){
//
//        // given
////        ActivityHistory saveActivityHistory = new ActivityHistory();
////        saveActivityHistory.setRegdate(new Date());
////
////        ActivityHistory activityHistory = activityHistoryRepository.save(saveActivityHistory);
////
////        Integer idx = activityHistory.getIdx();
////        System.out.println("저장한 idx : "+ idx);
//
//        // when
//        ActivityHistoryDto activityHistoryDto = activityHistoryService.findByActivityHistoryByIdx(1);
//        System.out.println("activityHistoryDto : "+ activityHistoryDto);
//
////        activityHistoryRepository.delete(activityHistory);
//
//    }

    // Function "DATE_FORMAT" not found; SQL statement: 에러발생
//    @Test
//    @DisplayName("활동내역 통계 테스트")
//    public void findByActivityHistoryStatisticsTest(){
//
//        // given
//        Integer companyIdx = 1;
//        int day = 1;
//
//        // when
//        ActivityHistoryStatisticsDto activityHistoryStatisticsDto = activityHistoryService.findByActivityHistoryStatistics(companyIdx, day);
//        System.out.println("activityHistoryStatisticsDto : "+ activityHistoryStatisticsDto);
//    }

    @Test
    @DisplayName("활동내역 저장 테스트")
    public void insertActivityHistoryTest() {

        int type = 1;
        int companyIdx = 1;
        int adminIdx = 1;
        int activityIdx = 1;
        String activityDetail = "저장테스트";
        String reason = "저장테스트2";
        String ipAddr = "ip테스트";
        int state = 1;

        Integer saveActivityHistoryIdx = activityHistoryService.insertActivityHistory(type, companyIdx, adminIdx, activityIdx, activityDetail, reason, ipAddr, state);
        System.out.println("저장된 IDX : "+saveActivityHistoryIdx);
    }




}