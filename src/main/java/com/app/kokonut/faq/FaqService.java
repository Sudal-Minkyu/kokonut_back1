package com.app.kokonut.faq;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.faq.dto.FaqDetailDto;
import com.app.kokonut.faq.dto.FaqListDto;
import com.app.kokonut.faq.dto.FaqSearchDto;
import com.app.kokonut.faq.entity.Faq;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
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
    public ResponseEntity<Map<String, Object>> faqList(String userRole, FaqSearchDto faqSearchDto, Pageable pageable) {
        log.info("faqList 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        log.info("자주 묻는 질문 목록 조회 userRole : " + userRole);
        Page<FaqListDto> faqListDtos = faqRepository.findFaqPage(userRole, faqSearchDto, pageable);
        data.put("faqListDtos", faqListDtos);
        return ResponseEntity.ok(res.ResponseEntityPage(faqListDtos));
    }

    public ResponseEntity<Map<String, Object>> faqDetail(String userRole, Integer idx) {
        log.info("faqDetail 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if("[SYSTEM]".equals(userRole)){
            if(idx != null){
                log.info("자주 묻는 질문 상세보기 idx: " + idx);
                FaqDetailDto faqDetailDto = faqRepository.findFaqByIdx(idx);
                if(faqDetailDto != null){
                    // 조회 성공
                    log.info("자주 묻는 질문 상세보기 조회 성공 idx: " + faqDetailDto.getIdx() + ", " + faqDetailDto.getQuestion());
                    data.put("faqDetailDto",  faqDetailDto);
                    return ResponseEntity.ok(res.success(data));
                }else{
                    // 조회 실패
                    log.error("해당 idx의 자주 묻는 질문 게시글을 조회할 수 없습니다. idx : "+idx);
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
                }
            }else{
                log.error("idx 값을 찾을 수 없습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
            }
        }else{
            log.error("접근권한이 없습니다. 권한 : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getCode()));
        }
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> faqSave(String userRole, String email, FaqDetailDto faqDetailDto) {
        log.info("faqSave 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if("[SYSTEM]".equals(userRole)){
            // 접속 정보에서 관리자 정보 가져오기, idx, name
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));
            Integer adminIdx = admin.getIdx();
            String adminName = admin.getName();

            if(faqDetailDto.getIdx() != null){
                log.info("자주 묻는 질문 등록");
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
                log.info("자주 묻는 질문 등록 완료. idx : " + savedIdx);
            }else{
                log.info("자주 묻는 질문 수정 idx : " + faqDetailDto.getIdx());
                FaqDetailDto updateDetailDto = faqRepository.findFaqByIdx(faqDetailDto.getIdx());
                log.info("수정자, 수정일시, 내용 세팅");
                Faq updateFaq = new Faq();

                updateFaq.setIdx(updateDetailDto.getIdx());

                updateFaq.setModifierIdx(adminIdx);
                updateFaq.setModifierName(adminName);
                updateFaq.setModifyDate(LocalDateTime.now());

                updateFaq.setQuestion(updateDetailDto.getQuestion());
                updateFaq.setAnswer(updateDetailDto.getAnswer());
                updateFaq.setType(updateDetailDto.getType());

                Integer updatedIdx = faqRepository.save(updateFaq).getIdx();
                log.info("자주 묻는 질문 수정 완료. idx : " + updatedIdx);
            }
            return ResponseEntity.ok(res.success(data));
        }else{
            log.error("접근권한이 없습니다. 권한 : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getCode()));
        }

    }

    public ResponseEntity<Map<String, Object>> faqDelete(String userRole, String email, Integer idx) {
        log.info("faqDelete 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if("[SYSTEM]".equals(userRole)){
            if(idx != null){
                log.info("자주 묻는 질문 삭제 시작.");
                faqRepository.deleteById(idx);
                log.info("자주 묻는 질문 삭제 완료. idx : "+idx);
                return ResponseEntity.ok(res.success(data));
            }else{
                log.error("idx를 확인해 주세요.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getCode()));
            }
        }else {
            log.error("접근권한이 없습니다. 권한 : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getCode()));
        }
    }
}
