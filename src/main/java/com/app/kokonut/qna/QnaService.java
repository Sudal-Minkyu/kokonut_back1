package com.app.kokonut.qna;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminEmailInfoDto;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.joy.email.MailSender;
import com.app.kokonut.qna.dto.*;
import com.app.kokonut.qna.entity.Qna;
import com.app.kokonut.qnaFile.QnaFile;
import com.app.kokonut.qnaFile.QnaFileRepository;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import com.app.kokonut.woody.common.component.AwsS3Util;
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
import java.io.IOException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@Service
public class QnaService {
    private final QnaFileRepository qnaFileRepository;

    // 파일 등록
    // @Value("${kokonut.aws.s3.qnaS3Folder}")
    private String qnaS3Folder = "/qna-images/";

    //@Value("${kokonut.aws.s3.url}")
    private String AWSURL = "https://kokonut-dev-server.s3.ap-northeast-2.amazonaws.com";
    private final AwsS3Util awsS3Util;

    private final QnaRepository qnaRepository;
    private final AdminRepository adminRepository;
    private final MailSender mailSender;

    public QnaService(AwsS3Util awsS3Util, QnaRepository qnaRepository, AdminRepository adminRepository,
                      QnaFileRepository qnaFileRepository, MailSender mailSender) {
        this.awsS3Util = awsS3Util;
        this.qnaRepository = qnaRepository;
        this.adminRepository = adminRepository;
        this.qnaFileRepository = qnaFileRepository;
        this.mailSender = mailSender;
    }

    public ResponseEntity<Map<String, Object>> qnaList(QnaSearchDto qnaSearchDto, Pageable pageable) {
        log.info("qnaList 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        Page<QnaListDto> qnaListDtos = qnaRepository.findQnaPage(qnaSearchDto, pageable);

        return ResponseEntity.ok(res.ResponseEntityPage(qnaListDtos));
    }

    public ResponseEntity<Map<String, Object>> qnaDetail(Integer idx) {
        log.info("qnaDetail 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        if(idx == null){
            // 인덱스 값이 존재하지 않음
            log.error("idx 값을 찾을 수 없습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
        }else{
            // 조회
            log.info("1:1 문의 게시글 상세보기");
            QnaDetailDto qnaDetailDto = qnaRepository.findQnaByIdx(idx);
            // 조회결과
            if(qnaDetailDto == null){
                // 조회 실패
                log.error("해당 idx의 1:1 문의 게시글을 조회할 수 없습니다. 문의 게시글 idx : "+idx);
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
            }else{
                // 조회 성공
                    // TODO roll에 따라서 시스템 관리자 접속이 아닌 경우 (사업자 혹은 관리자) 접속한 계정의 idx와 상세보기 게시글의 adminIdx가 일치하지 않으면 조회하지 못하도록 처리
                    // log.error("본인이 작성한 문의글만 확인이 가능합니다.");
                    // return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
                log.info("1:1 문의 게시글 상세보기 조회 성공 : " + qnaDetailDto.getIdx() + ", " + qnaDetailDto.getContent());

                // TODO 첨부 파일 목록 조회
                data.put("qnaDetailDto",  qnaDetailDto);
                return ResponseEntity.ok(res.success(data));
            }
        }
    }
    @Transactional
    public ResponseEntity<Map<String, Object>> questionSave(String email, QnaQuestionSaveDto qnaQuestionSaveDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("questionSave 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        // 문의글 내용 등록
        log.info("1:1 문의 게시글 등록 시작");
        Qna qna = new Qna();
        Qna saveQna = new Qna();

        // 접속 정보에서 idx 가져오기
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));

        // 1:1 문의 등록
        qna.setAdminIdx(admin.getIdx());
        qna.setTitle(qnaQuestionSaveDto.getTitle());
        qna.setContent(qnaQuestionSaveDto.getContent());
        qna.setType(qnaQuestionSaveDto.getType());
        qna.setRegdate(LocalDateTime.now());
        saveQna = qnaRepository.save(qna);
        log.info("1:1 문의 게시글 등록 완료 saveQna : "+saveQna.getIdx());

        // 파일 업로드 처리
        log.info("파일 업로드 처리 시작 saveQna : "+saveQna.getIdx());
        List<MultipartFile> multipartFiles = qnaQuestionSaveDto.getMultipartFiles();
        if(multipartFiles.isEmpty()){
            log.info("첨부파일 없음.");
        }else {
            log.info("첨부파일 있음. 파일 업로드 시작. multipartFiles 처리해야할 건 수 : "+multipartFiles.size());
            for (MultipartFile multipartFile: multipartFiles) {
                QnaFile qnaFile = new QnaFile();
                qnaFile.setQnaIdx(saveQna.getIdx());

                // file original name
                String originalFilename = Normalizer.normalize(Objects.requireNonNull(multipartFile.getOriginalFilename()), Normalizer.Form.NFC);
                log.info("originalFilename : "+originalFilename);
                qnaFile.setCfOriginalFilename(originalFilename);

                // file size
                long fileSize = multipartFile.getSize();
                log.info("fileSize : "+fileSize);

                // file extension (확장자)
                String ext;
                ext = '.'+originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                log.info("ext : "+ext);

                // file name 서버 저장 시 중복 명 처리
                String fileName = UUID.randomUUID().toString().replace("-", "")+ext;
                log.info("fileName : "+fileName);
                qnaFile.setCfFilename(fileName);

                // S3에 저장 할 파일 주소
                SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
                String filePath = AWSURL+qnaS3Folder+date.format(new Date());
                log.info("filePath : "+filePath);
                qnaFile.setCfPath(filePath);

                // S3에 파일 업로드
                String storedFileName = awsS3Util.imageFileUpload(multipartFile, fileName, qnaS3Folder+date.format(new Date()));
                if(storedFileName == null) {
                    log.error("이미지 업로드를 실패했습니다. -관리자에게 문의해주세요-");
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO039.getCode(), ResponseErrorCode.KO039.getDesc()));
                } else {
                    // 파일 저장
                    qnaFileRepository.save(qnaFile);
                    log.info("첨부 파일 저장에 성공햇습니다.");
                }
            }// end of loop
        }

        // 메일 전송 (모든 시스템 관리자에게 알림 메일 전송)
        log.info("시스템 관리자들에게 문의 등록 안내 메일 전송 시작");
        List<AdminEmailInfoDto> systemAdminInfos = adminRepository.findSystemAdminEmailInfo();
        if(!systemAdminInfos.isEmpty()){
            // 메일 내용 작성
            String mailData = URLEncoder.encode(email, "UTF-8");
            String title = "문의하기 등록 알림";
            // 메일 전송을 위해 HTML형태로 내용을 만들어서 만들어진 화면에 붙여서 메일 보냄. TODO 화면단 만들어지면 추가 개발
            String contents = "문의하기 질문이 등록 되었습니다.<br> 등록자 이메일 : "+email;

            for(AdminEmailInfoDto systemAdminInfo : systemAdminInfos){
                String toEmail = systemAdminInfo.getEmail();
                String toName = systemAdminInfo.getName();
                log.info("toEmail" + toEmail + ", toName" + toName);
                if (toEmail == null || toName == null ){
                    // 받는메일, 받는사람이름 중 하나라도 조회되지 않으면 메일을 보내지 않음.
                    log.error("시스템관리자 메일 정보를 찾을 수 없습니다.");
                }else{
                    mailSender.sendMail(toEmail, toName, title, contents);
                }
            }
        }else{
            log.error("시스템관리자를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(res.success(data));
    }
    @Transactional
    public ResponseEntity<Map<String, Object>> answerSave(String email, QnaAnswerSaveDto qnaAnswerSaveDto) throws IOException {
        log.info("answerSave 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        // 문의글 답변 내용 등록
        log.info("1:1 문의 게시글 답변 등록 시작");
        Qna saveQna = new Qna();

        // 접속 정보에서 idx 가져오기
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));

        if(qnaAnswerSaveDto.getIdx() != null){
            //저장내용 세팅
            Optional<Qna> savedQna = qnaRepository.findById(qnaAnswerSaveDto.getIdx());
            if(savedQna.isEmpty()){
                log.error("해당 문의글을 찾을 수 없습니다. 문의글 idx : " + qnaAnswerSaveDto.getIdx());
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getDesc()));
            }else{
                saveQna.setIdx(qnaAnswerSaveDto.getIdx());
                // 문의 내용
                saveQna.setAdminIdx(savedQna.get().getAdminIdx());
                saveQna.setTitle(savedQna.get().getTitle());
                saveQna.setContent(savedQna.get().getContent());
                saveQna.setType(savedQna.get().getType());
                saveQna.setRegdate(savedQna.get().getRegdate());
                // 답변 내용
                saveQna.setAnsIdx(admin.getIdx());
                saveQna.setState(1);
                // TODO answer XssPreventer 스프링부트에서 적용하기
                saveQna.setAnswer(qnaAnswerSaveDto.getAnswer());
                saveQna.setAnswerDate(LocalDateTime.now());
                qnaRepository.save(saveQna);
                log.info("1:1 문의 게시글 답변 등록이 완료되었습니다. : " +  saveQna.getAnswer() + " idx : "+saveQna.getIdx());

                // 답변 등록 안내 메일 보내기
                String title = "[Kokonut] 문의글 답변 등록 안내 메일입니다.";
                String contents = "문의하신 글에 답변이 등록되었습니다.<br> 답변내용 : "+saveQna.getAnswer();
                // 기존 내용 조회 후 해당 내용을 화면에 붙여서 메일 보내줌 TODO 화면단 만들어지면 추가 개발
                mailSender.sendMail(email, null, title, contents);
            }
        }else{
            log.error("답변을 등록할 문의글을 확인해주세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getDesc()));
        }
        return ResponseEntity.ok(res.success(data));
    };
}
