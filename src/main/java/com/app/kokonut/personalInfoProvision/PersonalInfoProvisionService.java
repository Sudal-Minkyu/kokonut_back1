package com.app.kokonut.personalInfoProvision;

import com.app.kokonut.activityHistory.ActivityHistoryService;
import com.app.kokonut.activityHistory.dto.ActivityCode;
import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;
import com.app.kokonut.common.component.CommonUtil;
import com.app.kokonut.configs.KeyGenerateService;
import com.app.kokonut.personalInfoProvision.dtos.PersonalInfoProvisionListDto;
import com.app.kokonut.personalInfoProvision.dtos.PersonalInfoProvisionMapperDto;
import com.app.kokonut.personalInfoProvision.dtos.PersonalInfoProvisionSaveDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Woody
 * Date : 2023-01-17
 * Remark :
 */
@Slf4j
@Service
public class PersonalInfoProvisionService {

    private final KeyGenerateService keyGenerateService;

    private final ActivityHistoryService activityHistoryService;

    private final AdminRepository adminRepository;
    private final PersonalInfoProvisionRepository personalInfoProvisionRepository;

    @Autowired
    public PersonalInfoProvisionService(KeyGenerateService keyGenerateService, ActivityHistoryService activityHistoryService,
                                        AdminRepository adminRepository, PersonalInfoProvisionRepository personalInfoProvisionRepository){
        this.keyGenerateService = keyGenerateService;
        this.activityHistoryService = activityHistoryService;
        this.adminRepository = adminRepository;
        this.personalInfoProvisionRepository = personalInfoProvisionRepository;
    }

    /**
     * 정보제공 목록 조회
     */
    public List<PersonalInfoProvisionListDto> findByProvisionList(PersonalInfoProvisionMapperDto personalInfoProvisionMapperDto) {
        log.info("findByProvisionList 호출");
        return personalInfoProvisionRepository.findByProvisionList(personalInfoProvisionMapperDto);
    }

    /**
     * 정보제공 등록
     */
    public ResponseEntity<Map<String, Object>> privisionSave(PersonalInfoProvisionSaveDto personalInfoProvisionSaveDto, String email) {
        log.info("privisionSave 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info("personalInfoProvisionSaveDto : " + personalInfoProvisionSaveDto);
        log.info("email : " + email);

        // 해당 이메일을 통해 회사 IDX 조회
        AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

        int adminIdx;
        int companyIdx;
        String businessNumber;

        if (adminCompanyInfoDto == null) {
            log.error("이메일 정보가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 " + ResponseErrorCode.KO004.getDesc()));
        } else {
            adminIdx = adminCompanyInfoDto.getAdminIdx(); // modifierIdx
            companyIdx = adminCompanyInfoDto.getCompanyIdx(); // companyIdx
            businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
        }


        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 고유넘버
        String number = keyGenerateService.keyGenerate("personal_info_provision", nowDate+businessNumber+nowDate, String.valueOf(adminIdx));


//        // 별도수집인 경우 별도수집 대상 목록을 미리 저장한다.
//        if(data.getRecipientType() == 2 && (data.getType() == 1 || data.getType() == 2) && data.getAgreeYn() == 'Y' && data.getAgreeType() == 2) {
//            String tableName = dynamicUserService.SelectTableName(data.getCompanyIdx());
//            List<Map<String, Object>> userList = dynamicUserService.SelectUserListByTableName(tableName);
//
//            if(data.getTargetStatus().equals("ALL")) {
//                List<String> idList = new ArrayList<String>();
//                for(Map<String, Object> userInfo : userList) {
//                    String id = userInfo.get("ID").toString();
//                    idList.add(id);
//                }
//
//                personalInfoService.saveTempProvisionAgree(number, idList);
//            } else {
//                List<String> idList = new ArrayList<String>();
//
//                String[] userIdxList = data.getTargets().split(",");
//                for(String userIdx : userIdxList) {
//                    for(int i = userList.size()-1; i >= 0; i--) {
//                        Map<String, Object> userInfo = userList.get(i);
//                        if(userInfo.get("IDX").toString().equals(userIdx)) {
//                            idList.add(userInfo.get("ID").toString());
//                            userList.remove(i);
//                            break;
//                        }
//                    }
//                }
//
//                personalInfoService.saveTempProvisionAgree(number, idList);
//            }
//        }
//
//        // 받는사람에게 이메일 전송
//        personalInfoService.sendEmailToRecipient(
//                request,
//                number,
//                data.getRecipientEmail(),
//                data.getStartDate(),
//                data.getExpDate(),
//                data.getPeriod());

        // 정보제공 등록 코드
        ActivityCode activityCode = ActivityCode.AC_21;
        // 활동이력 저장 -> 비정상 모드
        String ip = CommonUtil.clientIp();
        Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(4, companyIdx, adminIdx, activityCode, businessNumber + " - " + activityCode.getDesc() + " 시도 이력", "", ip, 0);

//        if () {
//            activityHistoryService.updateActivityHistory(activityHistoryIDX,
//                    businessNumber + " - " + activityCode.getDesc() + " 완료 이력", "", 1);
//        } else {
//            activityHistoryService.updateActivityHistory(activityHistoryIDX,
//                    businessNumber + " - " + activityCode.getDesc() + " 실패 이력", "필드 삭제 조건에 부합하지 않습니다.", 1);
//        }

        return ResponseEntity.ok(res.success(data));
    }
}
