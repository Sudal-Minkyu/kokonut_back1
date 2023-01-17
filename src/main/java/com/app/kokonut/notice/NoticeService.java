package com.app.kokonut.notice;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.notice.dto.*;
import com.app.kokonut.notice.entity.Notice;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Joy
 * Date : 2022-12-27
 * Time :
 * Remark : NoticeService 공지사항 서비스
 */
@Slf4j
@Service
public class NoticeService {
    private final AdminRepository adminRepository;
    private final NoticeRepository noticeRepository;
    public NoticeService(NoticeRepository noticeRepository,
                         AdminRepository adminRepository) {
        this.noticeRepository = noticeRepository;
        this.adminRepository = adminRepository;
    }

    /**
     * 공지사항 목록 조회
     * @param userRole        사용자 권한
     * @param noticeSearchDto 공지사항 검색 조건
     * @param pageable        페이징 처리를 위한 정보
     */
    public ResponseEntity<Map<String, Object>> noticeList(String userRole, NoticeSearchDto noticeSearchDto, Pageable pageable) {
        log.info("noticeList 호출, userRole : "+ userRole);
        AjaxResponse res = new AjaxResponse();
        if("[SYSTEM]".equals(userRole)){
            log.info("공지사항 목록 조회");
            Page<NoticeListDto> noticeListDtos = noticeRepository.findNoticePage(noticeSearchDto, pageable);
            return ResponseEntity.ok(res.ResponseEntityPage(noticeListDtos));
        }else{
            log.info("공지사항 목록(제목+내용, 게시중) 조회");
            Page<NoticeContentListDto> noticeListDtos = noticeRepository.findNoticeContentPage(pageable);
            return ResponseEntity.ok(res.ResponseEntityPage(noticeListDtos));
        }
    }

    /**
     * 공지사항 상세 조회
     * @param userRole 사용자 권한
     * @param idx      공지사항 인덱스
     */
    public ResponseEntity<Map<String, Object>> noticeDetail(String userRole, Integer idx) {
        log.info("noticeDetail 호출, userRole" + userRole);
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if(!"[SYSTEM]".equals(userRole)){
            log.error("접근 권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }else{
            if(idx != null){
                log.info("공지사항 상세 조회, idx : " + idx);
                NoticeDetailDto noticeDetailDto = noticeRepository.findNoticeByIdx(idx);
                if(noticeDetailDto != null){
                    log.info("공지사항 상세 조회 성공, idx : " + noticeDetailDto.getIdx() + ", Title : " + noticeDetailDto.getTitle());
                    data.put("noticeDetailDto",  noticeDetailDto);

                    // viewCount 증가
                    Notice notice = noticeRepository.findById(idx)
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 'Admin' 입니다."));
                    notice.setViewCount(notice.getViewCount()+1);
                    noticeRepository.save(notice);

                    return ResponseEntity.ok(res.success(data));
                }else{
                    log.error("공지사항 상세 조회 실패, idx : "+idx);
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO048.getCode(), ResponseErrorCode.KO048.getDesc()));
                }
            }else{
                log.error("idx 값을 확인 할 수 없습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO047.getCode(), ResponseErrorCode.KO047.getDesc()));
            }
        }
    }

    /**
     * 공지사항 저장
     * @param userRole 사용자 권한
     * @param email    사용자 이메일
     * @param noticeDetailDto 공지사항 상세 내용
     */
    @Transactional
    public ResponseEntity<Map<String, Object>> noticeSave(String userRole, String email, NoticeDetailDto noticeDetailDto) {
        log.info("noticeSave 호출, userRole : "+ userRole);
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

            if(noticeDetailDto.getIdx() == null){
                log.info("공지사항 등록");
                NoticeDetailDto insertDetailDto = noticeDetailDto;

                log.info("등록자, 등록일시, 내용 세팅");
                Notice insertNotice = new Notice();
                insertNotice.setAdminIdx(adminIdx);
                insertNotice.setRegisterName(adminName);
                insertNotice.setRegdate(LocalDateTime.now());

                insertNotice.setIsNotice(insertDetailDto.getIsNotice());
                insertNotice.setRegistDate(insertDetailDto.getRegistDate());
                insertNotice.setTitle(insertDetailDto.getTitle());
                insertNotice.setContent(insertDetailDto.getContent());
                insertNotice.setState(1);
                Integer savedIdx = noticeRepository.save(insertNotice).getIdx();
                log.info("공지사항 등록 완료. idx : " + savedIdx);
            }else{
                log.info("공지사항 수정, idx : " + noticeDetailDto.getIdx());
                Optional<Notice> updateNotice = noticeRepository.findById(noticeDetailDto.getIdx());
                if(updateNotice.isEmpty()){
                    log.error("공지사항 수정 실패, 게시글을 발견할 수 없습니다. 요청 idx : " + noticeDetailDto.getIdx());
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO048.getCode(), ResponseErrorCode.KO048.getDesc()));
                }else{
                    log.info("수정자, 수정일시 세팅 내용 세팅");
                    updateNotice.get().setModifierIdx(adminIdx);
                    updateNotice.get().setModifierName(adminName);
                    updateNotice.get().setModifyDate(LocalDateTime.now());

                    log.info("수정 내용 세팅");
                    if(noticeDetailDto.getIsNotice() != null){
                        updateNotice.get().setIsNotice(noticeDetailDto.getIsNotice());
                    }
                    if(noticeDetailDto.getRegistDate() != null){
                        updateNotice.get().setRegistDate(noticeDetailDto.getRegistDate());
                    }
                    if(noticeDetailDto.getTitle() != null){
                        updateNotice.get().setTitle(noticeDetailDto.getTitle());
                    }
                    if(noticeDetailDto.getContent() != null){
                        updateNotice.get().setContent(noticeDetailDto.getContent());
                    }
                    if(noticeDetailDto.getState() != null){
                        updateNotice.get().setState(noticeDetailDto.getState());
                    }

                    Integer updatedIdx = noticeRepository.save(updateNotice.get()).getIdx();
                    log.info("공지사항 수정 완료. idx : " + updatedIdx);
                }
            }
            return ResponseEntity.ok(res.success(data));
        }
    }

    /**
     * 공지사항 삭제
     * @param userRole 사용자 권한
     * @param email    사용자 이메일
     * @param idx      공지사항 인덱스
     */
    @Transactional
    public ResponseEntity<Map<String, Object>> noticeDelete(String userRole, String email, Integer idx) {
        log.info("noticeDelete 호출, userRole : " +userRole);
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if(!"[SYSTEM]".equals(userRole)){
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }else {
            if(idx != null){
                log.info("공지사항 삭제 시작.");
                noticeRepository.deleteById(idx);
                log.info("공지사항 삭제 완료. idx : "+idx);
                return ResponseEntity.ok(res.success(data));
            }else{
                log.error("idx 값을 확인 할 수 없습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO047.getCode(), ResponseErrorCode.KO047.getDesc()));
            }
        }
    }

    /**
     * 공지사항 상태 변경
     * @param userRole 사용자 권한
     * @param email    사용자 이메일
     * @param noticeStateDto 공지사항 상태값 정보
     */
    @Transactional
    public ResponseEntity<Map<String, Object>> noticeState(String userRole, String email, NoticeStateDto noticeStateDto) {
        log.info("noticeDelete 호출, userRole : " +userRole);
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if(!"[SYSTEM]".equals(userRole)){
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getDesc()));
        }else {
            if(noticeStateDto.getIdx() == null){
                log.error("idx 값을 확인 할 수 없습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO047.getCode(), ResponseErrorCode.KO047.getDesc()));
            }else{
                log.info("공지사항 상태변경 시작.");
                Notice notice = noticeRepository.findById(noticeStateDto.getIdx())
                        .orElseThrow(() -> new UsernameNotFoundException("해당하는 공지사항을 찾을 수 없습니다. : "+noticeStateDto.getIdx()));

                Integer state = noticeStateDto.getState();
                if(state == 1){
                    notice.setStopDate(LocalDateTime.MIN);
                }else{
                    notice.setStopDate(LocalDateTime.now());
                }

                notice.setState(state);
                noticeRepository.save(notice);
                log.info("공지사항 상태변경 완료.");
                return ResponseEntity.ok(res.success(data));
            }
        }
    }
}
