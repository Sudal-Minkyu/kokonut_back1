package com.app.kokonut.revisedDocument;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.revisedDocument.dto.RevDocSaveDto;
import com.app.kokonut.revisedDocument.dto.RevDocListDto;
import com.app.kokonut.revisedDocument.dto.RevDocSearchDto;
import com.app.kokonut.revisedDocument.entity.RevisedDocument;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : RevisedDocumentService 개인정보 처리방침 - 개정문서 서비스
 */
@Slf4j
@Service
public class RevisedDocumentService {
    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();

    private final RevisedDocumentRepository revDocRepository;
    private final AdminRepository adminRepository;

    public RevisedDocumentService(RevisedDocumentRepository revDocRepository, AdminRepository adminRepository) {
        this.revDocRepository = revDocRepository;
        this.adminRepository = adminRepository;
    }

    public ResponseEntity<Map<String, Object>> revDocList(String userRole, String email, RevDocSearchDto revDocSearchDto, Pageable pageable) {
        log.info("revDocList 호출, userRole : " + userRole);
        // 접속 정보에서 idx 가져오기
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));
        Integer adminIdx = admin.getIdx();
        String adminName = admin.getName();
        // TODO 메뉴 권한에 대한 체크 부분
        if ("[MASTER]".equals(userRole) || "[ADMIN]".equals(userRole)) {
            Integer companyIdx = admin.getCompanyIdx();
            log.info("개정문서 목록 조회");
            Page<RevDocListDto> revDocListDtos = revDocRepository.findRevDocPage(companyIdx, revDocSearchDto, pageable);
            return ResponseEntity.ok(res.ResponseEntityPage(revDocListDtos));
        } else {
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getCode()));
        }
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> revDocSave(String userRole, String email, RevDocSaveDto revDocSaveDto, HttpServletRequest request, HttpServletResponse response) {
        log.info("revDocSave 호출, userRole : " + userRole);
        // TODO 메뉴 권한에 대한 체크 부분
        if ("[MASTER]".equals(userRole) || "[ADMIN]".equals(userRole)) {
            log.info("개정문서 등록 시작");
            RevisedDocument revDoc = new RevisedDocument();

            // 접속 정보에서 idx 가져오기
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));
            Integer adminIdx = admin.getIdx();
            String adminName = admin.getName();
            Integer companyIdx =admin.getCompanyIdx();

            revDoc.setAdminIdx(adminIdx);
            revDoc.setRegisterName(adminName);
            revDoc.setRegdate(LocalDateTime.now());
            revDoc.setCompanyIdx(companyIdx);
            revDoc.setEnforceStartDate(revDocSaveDto.getEnforceStartDate());
            revDoc.setEnforceEndDate(revDocSaveDto.getEnforceEndDate());

            Integer savedIdx = revDocRepository.save(revDoc).getIdx();
            log.info("개정문서 등록 완료, idx : "+ savedIdx);

            // 파일 업로드 처리
            log.info("개정문서 파일 업로드 처리 시작, idx : "+savedIdx);
            MultipartFile multipartFile = revDocSaveDto.getMultipartFile();
            if(multipartFile.isEmpty()){
                log.info("첨부파일 없음.");
            }else{
                log.info("첨부파일 있음. 파일 업로드 시작.");
            }
                // 파일 업로드 처리
//                log.info("파일 업로드 처리 시작 saveQna : "+saveQna.getIdx());
//                List<MultipartFile> multipartFiles = qnaQuestionSaveDto.getMultipartFiles();
//                if(multipartFiles.isEmpty()){
//                    log.info("첨부파일 없음.");
//                }else {
//                    log.info("첨부파일 있음. 파일 업로드 시작. multipartFiles 처리해야할 건 수 : "+multipartFiles.size());
//                    for (MultipartFile multipartFile: multipartFiles) {
//                        QnaFile qnaFile = new QnaFile();
//                        qnaFile.setQnaIdx(saveQna.getIdx());
//
//                        // file original name
//                        String originalFilename = Normalizer.normalize(Objects.requireNonNull(multipartFile.getOriginalFilename()), Normalizer.Form.NFC);
//                        log.info("originalFilename : "+originalFilename);
//                        qnaFile.setCfOriginalFilename(originalFilename);
//
//                        // file size
//                        long fileSize = multipartFile.getSize();
//                        log.info("fileSize : "+fileSize);
//
//                        // file extension (확장자)
//                        String ext;
//                        ext = '.'+originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//                        log.info("ext : "+ext);
//
//                        // file name 서버 저장 시 중복 명 처리
//                        String fileName = UUID.randomUUID().toString().replace("-", "")+ext;
//                        log.info("fileName : "+fileName);
//                        qnaFile.setCfFilename(fileName);
//
//                        // S3에 저장 할 파일 주소
//                        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
//                        String filePath = AWSURL+qnaS3Folder+date.format(new Date());
//                        log.info("filePath : "+filePath);
//                        qnaFile.setCfPath(filePath);
//
//                        // S3에 파일 업로드
//                        String storedFileName = awsS3Util.imageFileUpload(multipartFile, fileName, qnaS3Folder+date.format(new Date()));
//                        if(storedFileName == null) {
//                            log.error("이미지 업로드를 실패했습니다. -관리자에게 문의해주세요-");
//                            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO039.getCode(), ResponseErrorCode.KO039.getDesc()));
//                        } else {
//                            // 파일 저장
//                            qnaFileRepository.save(qnaFile);
//                            log.info("첨부 파일 저장에 성공햇습니다.");
//                        }
//                    }// end of loop
//                }
                return ResponseEntity.ok(res.success(data));
        } else {
            log.error("접근권한이 없습니다. userRole : " + userRole);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO001.getCode(), ResponseErrorCode.KO001.getCode()));
        }
    }
}
