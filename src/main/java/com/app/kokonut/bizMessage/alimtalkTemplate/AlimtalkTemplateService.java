package com.app.kokonut.bizMessage.alimtalkTemplate;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.auth.jwt.util.SecurityUtil;
import com.app.kokonut.bizMessage.alimtalkTemplate.dto.*;
import com.app.kokonut.bizMessage.kakaoChannel.KakaoChannelRepository;
import com.app.kokonut.bizMessage.kakaoChannel.dto.KakaoChannelByChannelIdListDto;
import com.app.kokonut.bizMessage.navercloud.NaverCloudPlatformResultDto;
import com.app.kokonut.bizMessage.navercloud.NaverCloudPlatformService;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Woody
 * Date : 2022-12-15
 * Time :
 * Remark : 알림톡 템플릿 Service
 */
@Slf4j
@Service
public class AlimtalkTemplateService {

    private final NaverCloudPlatformService naverCloudPlatformService;

    private final AdminRepository adminRepository;
    private final AlimtalkTemplateRepository alimtalkTemplateRepository;
    private final KakaoChannelRepository kakaoChannelRepository;

    @Autowired
    public AlimtalkTemplateService(NaverCloudPlatformService naverCloudPlatformService, AdminRepository adminRepository,
                                   AlimtalkTemplateRepository alimtalkTemplateRepository, KakaoChannelRepository kakaoChannelRepository) {
        this.naverCloudPlatformService = naverCloudPlatformService;
        this.adminRepository = adminRepository;
        this.alimtalkTemplateRepository = alimtalkTemplateRepository;
        this.kakaoChannelRepository = kakaoChannelRepository;
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> alimTalkTemplateList(AlimtalkTemplateSearchDto alimtalkTemplateSearchDto, Pageable pageable) throws Exception {

        log.info("alimTalkTemplateList 조회");

        AjaxResponse res = new AjaxResponse();

        String email = SecurityUtil.getCurrentUserEmail();

        // 해당 이메일을 통해 회사 IDX 조회
        int companyIdx = adminRepository.findByCompanyInfo(email).getCompanyIdx();

        List<KakaoChannelByChannelIdListDto> kakaoChannelByChannelIdListDtos = kakaoChannelRepository.findByKakaoChannelIdList(companyIdx);

        List<AlimtalkTemplate> alimtalkTemplateList = new ArrayList<>();
        for (KakaoChannelByChannelIdListDto kakaoChannelByChannelIdListDto : kakaoChannelByChannelIdListDtos) {
            String channelId = kakaoChannelByChannelIdListDto.getChannelId();

            List<AlimtalkTemplateInfoListDto> alimtalkTemplateInfoListDtos = alimtalkTemplateRepository.findByAlimtalkTemplateInfoList(companyIdx, channelId, "1");

            for (AlimtalkTemplateInfoListDto alimtalkTemplateInfoListDto : alimtalkTemplateInfoListDtos) {

                String templateCode = alimtalkTemplateInfoListDto.getTemplateCode();
                String status = alimtalkTemplateInfoListDto.getStatus();

                // 상태 값이 변경 가능한 상태면 계속 상태 값 조회 - 상태값 : ACCEPT - 수락 REGISTER - 등록 INSPECT - 검수 중 COMPLETE - 완료 REJECT - 반려
                if (!status.equals("COMPLETE") && !status.equals("REJECT")) {

                    NaverCloudPlatformResultDto result = naverCloudPlatformService.getTemplates(channelId, templateCode, "");

                    if (result.getResultCode().equals(200)) {

                        List<Map<String, Object>> response = new ObjectMapper().readValue(result.getResultText(), new TypeReference<>() {});

                        String currentState = response.get(0).get("templateInspectionStatus").toString();
                        String templateName = response.get(0).get("templateName").toString();
                        log.info("currentState : " + currentState);
                        log.info("templateName : " + templateName);

                        Optional<AlimtalkTemplate> optionalAlimtalkTemplate = alimtalkTemplateRepository.findByAlimtalkTemplate(templateCode, channelId, companyIdx);
                        if (optionalAlimtalkTemplate.isPresent()) {
                            optionalAlimtalkTemplate.get().setTemplateName(templateName);
                            optionalAlimtalkTemplate.get().setStatus(currentState);
                            alimtalkTemplateList.add(optionalAlimtalkTemplate.get());
                        }
                    }
                }
            }
        }

        // 조회 전 템플릿 상태 업데이트
        alimtalkTemplateRepository.saveAll(alimtalkTemplateList);

        Page<AlimtalkTemplateListDto> alimtalkTemplateListDtos = alimtalkTemplateRepository.findByAlimtalkTemplatePage(alimtalkTemplateSearchDto, companyIdx, pageable);

        return ResponseEntity.ok(res.ResponseEntityPage(alimtalkTemplateListDtos));
    }

    @Transactional
    public ResponseEntity<Map<String,Object>> saveAlimTalkTemplate(AlimtalkTemplateSaveAndUpdateDto alimtalkTemplateSaveAndUpdateDto) {

        log.info("saveAlimTalkTemplate 호출");

        log.info("alimtalkTemplateSaveAndUpdateDto : "+ alimtalkTemplateSaveAndUpdateDto);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        NaverCloudPlatformResultDto result = naverCloudPlatformService.postTemplates(alimtalkTemplateSaveAndUpdateDto, "POST");

        if(result.getResultCode().equals(200)) {
            log.info("템플릿 저장성공 후 DB 인서트");

            String email = SecurityUtil.getCurrentUserEmail();

            // 해당 이메일을 통해 회사 IDX 조회
            int companyIdx = adminRepository.findByCompanyInfo(email).getCompanyIdx();

            AlimtalkTemplate alimtalkTemplate = new AlimtalkTemplate();
            alimtalkTemplate.setCompanyIdx(companyIdx);
            alimtalkTemplate.setChannelId(alimtalkTemplateSaveAndUpdateDto.getChannelId());
            alimtalkTemplate.setTemplateCode(alimtalkTemplateSaveAndUpdateDto.getTemplateCode());

            String messageType = alimtalkTemplateSaveAndUpdateDto.getMessageType();
            alimtalkTemplate.setMessageType(messageType);
            if(messageType.equals("EX")){
                alimtalkTemplate.setExtraContent(alimtalkTemplateSaveAndUpdateDto.getExtraContent());
            } else if(messageType.equals("AD")) {
                alimtalkTemplate.setAdContent(alimtalkTemplateSaveAndUpdateDto.getAdContent());
            }

            String emphasizeType = alimtalkTemplateSaveAndUpdateDto.getEmphasizeType();
            alimtalkTemplate.setEmphasizeType(emphasizeType);
            if(emphasizeType.equals("TEXT")){
                alimtalkTemplate.setEmphasizeTitle(alimtalkTemplateSaveAndUpdateDto.getEmphasizeTitle());
                alimtalkTemplate.setEmphasizeSubTitle(alimtalkTemplateSaveAndUpdateDto.getEmphasizeSubTitle());
            }

            alimtalkTemplate.setSecurityFlag(alimtalkTemplateSaveAndUpdateDto.getSecurityFlag());
            alimtalkTemplate.setRegdate(LocalDateTime.now());

            alimtalkTemplateRepository.save(alimtalkTemplate);
        }

        return ResponseEntity.ok(res.success(data));
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> modifyAlimTalkTemplate(AlimtalkTemplateSaveAndUpdateDto alimtalkTemplateSaveAndUpdateDto) {
        log.info("modifyAlimTalkTemplate 호출");

        log.info("alimtalkTemplateSaveAndUpdateDto : "+ alimtalkTemplateSaveAndUpdateDto);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        NaverCloudPlatformResultDto result = naverCloudPlatformService.postTemplates(alimtalkTemplateSaveAndUpdateDto, "PUT");

        if(result.getResultCode().equals(200)) {
            log.info("템플릿 수정 성공 후 DB 수정시작");

            String email = SecurityUtil.getCurrentUserEmail();

            // 해당 이메일을 통해 회사 IDX 조회
            int companyIdx = adminRepository.findByCompanyInfo(email).getCompanyIdx();

            Optional<AlimtalkTemplate> optionalAlimtalkTemplate
                    = alimtalkTemplateRepository.findByAlimtalkTemplate(alimtalkTemplateSaveAndUpdateDto.getTemplateCode(), alimtalkTemplateSaveAndUpdateDto.getChannelId(), companyIdx);
            if (optionalAlimtalkTemplate.isPresent()) {

                String messageType = alimtalkTemplateSaveAndUpdateDto.getMessageType();
                optionalAlimtalkTemplate.get().setMessageType(messageType);
                if(messageType.equals("EX")){
                    optionalAlimtalkTemplate.get().setExtraContent(alimtalkTemplateSaveAndUpdateDto.getExtraContent());
                } else if(messageType.equals("AD")) {
                    optionalAlimtalkTemplate.get().setAdContent(alimtalkTemplateSaveAndUpdateDto.getAdContent());
                }

                String emphasizeType = alimtalkTemplateSaveAndUpdateDto.getEmphasizeType();
                optionalAlimtalkTemplate.get().setEmphasizeType(emphasizeType);
                if(emphasizeType.equals("TEXT")){
                    optionalAlimtalkTemplate.get().setEmphasizeTitle(alimtalkTemplateSaveAndUpdateDto.getEmphasizeTitle());
                    optionalAlimtalkTemplate.get().setEmphasizeSubTitle(alimtalkTemplateSaveAndUpdateDto.getEmphasizeSubTitle());
                }

                optionalAlimtalkTemplate.get().setSecurityFlag(alimtalkTemplateSaveAndUpdateDto.getSecurityFlag());

                optionalAlimtalkTemplate.get().setModifyDate(LocalDateTime.now());

                alimtalkTemplateRepository.save(optionalAlimtalkTemplate.get());

                log.info("템플릿 수정 성공 후 DB 수정 성공");
            }
        }

        return ResponseEntity.ok(res.success(data));
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> deleteAlimTalkTemplates(AlimtalkTemplateDeleteDto alimtalkTemplateDeleteDto) {
        log.info("deleteAlimTalkTemplates 호출");

        log.info("alimtalkTemplateDeleteDto : "+ alimtalkTemplateDeleteDto);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String email = SecurityUtil.getCurrentUserEmail();

        // 해당 이메일을 통해 회사 IDX 조회
        int companyIdx = adminRepository.findByCompanyInfo(email).getCompanyIdx();

        Optional<AlimtalkTemplate> optionalAlimtalkTemplate
                = alimtalkTemplateRepository.findByAlimtalkTemplate(alimtalkTemplateDeleteDto.getTemplateCode(), alimtalkTemplateDeleteDto.getChannelId(), companyIdx);

        if(email.equals("test@kokonut.me")){
            log.error("체험하기모드는 이용할 수 없습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO000.getCode(), ResponseErrorCode.KO000.getDesc()));
        } else {
            String channelId = alimtalkTemplateDeleteDto.getChannelId();
            String templateCode = alimtalkTemplateDeleteDto.getTemplateCode();

            NaverCloudPlatformResultDto result = naverCloudPlatformService.deleteTemplates(channelId, templateCode);

            // 템플릿 삭제
            if(result.getResultCode().equals(200)) {

                if (optionalAlimtalkTemplate.isPresent()) {
                    alimtalkTemplateRepository.delete(optionalAlimtalkTemplate.get());
                    log.info("알림톡 템플릿 삭제 성공");
                }

            } else if(result.getResultCode().equals(404)) {
                // NCP에서 템플릿 삭제가 되었을경우
                log.error("404 에러 NCP에서 템플릿 삭제됬는지 확인해볼 것");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO021.getCode(), ResponseErrorCode.KO021.getDesc()));
            } else {
                log.error("템플릿 삭제 실패");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO022.getCode(), ResponseErrorCode.KO022.getDesc()));
            }

            // 기존의 코코넛코드
//            // 템플릿 삭제
//            if(result.get("responseCode").toString().equals("200")) {
//                alimTalkMessageService.DeleteAlimTalkTemplate(paramMap);
//                // NCP에서 템플릿 삭제가 되었을경우
//            } else if(result.get("responseCode").toString().equals("404")) {
//                alimTalkMessageService.DeleteAlimTalkTemplate(paramMap); // -> 이게 왜 NCP에서 삭제처리로 인식하는지?
//            }

            return ResponseEntity.ok(res.success(data));
        }
    }







}