package com.app.kokonut.faq;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;
import com.app.kokonut.faq.dto.FaqAnswerListDto;
import com.app.kokonut.faq.dto.FaqDetailDto;
import com.app.kokonut.faq.dto.FaqListDto;
import com.app.kokonut.faq.dto.FaqSearchDto;
import com.app.kokonut.faq.entity.Faq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class FaqService {
    private final AdminRepository adminRepository;
    private final FaqRepository faqRepository;
    public FaqService(FaqRepository faqRepository,
                      AdminRepository adminRepository) {
        this.faqRepository = faqRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * 자주묻는질문 목록 보기
     * @param userRole      권한
     * @param faqSearchDto  자주 묻는 질문 검색 조건
     * @param pageable      페이징 처리를 위한 정보
     */
    public ResponseEntity<Map<String, Object>> faqList(String userRole, FaqSearchDto faqSearchDto, Pageable pageable) {
        log.info("faqList 호출, userRole : "+ userRole);
        AjaxResponse res = new AjaxResponse();
        if("[SYSTEM]".equals(userRole)){
            log.info("자주묻는 질문 목록 조회");
            Page<FaqListDto> faqListDtos = faqRepository.findFaqPage(faqSearchDto, pageable);
            return ResponseEntity.ok(res.ResponseEntityPage(faqListDtos));
        }else{
            log.info("자주묻는 질문 목록(질문+답변, 게시중) 조회");
            Page<FaqAnswerListDto> faqListDtos = faqRepository.findFaqAnswerPage(pageable);
            return ResponseEntity.ok(res.ResponseEntityPage(faqListDtos));
        }
    }

    /**
     * 자주묻는질문 상세 보기
     * @param userRole  권한
     * @param idx       자주 묻는 질문 인덱스
     */
    public ResponseEntity<Map<String, Object>> faqDetail(String userRole, Integer idx) {
        log.info("faqDetail 호출, userRole : "+ userRole);
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if(!"[SYSTEM]".equals(userRole)){
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }else{
            if(idx == null){
                log.error("idx 값을 확인 할 수 없습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO049.getCode(), ResponseErrorCode.KO049.getDesc()));
            }else{
                log.info("자주묻는 질문 상세 조회, idx : " + idx);
                FaqDetailDto faqDetailDto = faqRepository.findFaqByIdx(idx);
                if(faqDetailDto != null){
                    log.info("자주묻는 질문 상세 조회 성공, idx : " + faqDetailDto.getIdx() + ", Question : " + faqDetailDto.getQuestion());
                    data.put("faqDetailDto",  faqDetailDto);
                    return ResponseEntity.ok(res.success(data));
                }else{
                    log.error("자주묻는 질문 상세 조회 실패, idx : " +idx);
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO050.getCode(), ResponseErrorCode.KO050.getDesc()));
                }
            }
        }
    }

    /**
     * 자주묻는질문 등록/수정
     * @param userRole      권한
     * @param email         자주묻는 질문 등록/수정자 email
     * @param faqDetailDto  자주묻는 질문 상세 내용
     */
    @Transactional
    public ResponseEntity<Map<String, Object>> faqSave(String userRole, String email, FaqDetailDto faqDetailDto) {
        log.info("faqSave 호출, userRole : "+userRole);
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if(!"[SYSTEM]".equals(userRole)){
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }else{
            // 접속 정보에서 관리자 정보 가져오기, idx, name
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));
            Integer adminIdx = admin.getIdx();
            String adminName = admin.getName();

            if(faqDetailDto.getIdx() != null){
                log.info("자주묻는 질문 등록");
                FaqDetailDto insertDetailDto = faqDetailDto;
                log.info("등록자, 등록일시, 내용 세팅");
                Faq insertFaq = new Faq();

                insertFaq.setAdminIdx(adminIdx);
                insertFaq.setRegisterName(adminName);
                insertFaq.setRegdate(LocalDateTime.now());

                insertFaq.setQuestion(insertDetailDto.getQuestion());
                insertFaq.setAnswer(insertDetailDto.getAnswer());
                insertFaq.setType(insertDetailDto.getType());

                Integer savedIdx = faqRepository.save(insertFaq).getIdx();
                log.info("자주묻는 질문 등록 완료. idx : " + savedIdx);
            }else{
                log.info("자주묻는 질문 수정, idx : " + faqDetailDto.getIdx());
                Optional<Faq> updateFaq = faqRepository.findById(faqDetailDto.getIdx());
                if(updateFaq.isEmpty()){
                    log.error("자주묻는 질문 수정 실패, 게시글을 발견할 수 없습니다. 요청 idx : " + faqDetailDto.getIdx());
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO050.getCode(), ResponseErrorCode.KO050.getDesc()));
                }else{
                    log.info("수정자, 수정일시 세팅");
                    updateFaq.get().setModifierIdx(adminIdx);
                    updateFaq.get().setModifierName(adminName);
                    updateFaq.get().setModifyDate(LocalDateTime.now());
                    log.info("내용 세팅");
                    if(faqDetailDto.getQuestion() != null){
                        updateFaq.get().setQuestion(faqDetailDto.getQuestion());
                    }
                    if(faqDetailDto.getAnswer() != null){
                        updateFaq.get().setAnswer(faqDetailDto.getAnswer());
                    }
                    if(faqDetailDto.getType() != null){
                        updateFaq.get().setType(faqDetailDto.getType());
                    }
                    Integer updatedIdx = faqRepository.save(updateFaq.get()).getIdx();
                    log.info("자주묻는 질문 수정 완료. idx : " + updatedIdx);
                }
            }
            return ResponseEntity.ok(res.success(data));
        }
    }

    /**
     * 자주묻는질문 삭제
     * @param userRole      권한
     * @param email         자주묻는 질문 삭제자 email
     * @param idx  자주묻는 질문 인덱스
     */
    @Transactional
    public ResponseEntity<Map<String, Object>> faqDelete(String userRole, String email, Integer idx) {
        log.info("faqDelete 호출, userRole : " +userRole);
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if(!"[SYSTEM]".equals(userRole)){
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }else {
            if(idx == null){
                log.error("idx 값을 확인 할 수 없습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO049.getCode(), ResponseErrorCode.KO049.getDesc()));
            }else{
                log.info("자주묻는 질문 삭제 시작.");
                faqRepository.deleteById(idx);
                log.info("자주묻는 질문 삭제 완료. idx : "+idx);
                return ResponseEntity.ok(res.success(data));
            }
        }
    }
}
