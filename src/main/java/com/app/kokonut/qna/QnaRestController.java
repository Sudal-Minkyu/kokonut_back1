package com.app.kokonut.qna;

import com.app.kokonut.auth.jwt.util.SecurityUtil;
import com.app.kokonut.qna.dto.QnaAnswerSaveDto;
import com.app.kokonut.qna.dto.QnaQuestionSaveDto;
import com.app.kokonut.qna.dto.QnaSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/api/Qna")
public class QnaRestController {
    // 기존 코코넛 SystemQnaController 컨트롤러 리팩토링
    // 기존 url : /system/qna , 변경 url : /api/Qna

    // 기존 코코넛 MemberQnaController 컨트롤러 리팩토링
    // 기존 url : /member/qna" , 변경 url : /api/Qna

    private final QnaService qnaService;

    public QnaRestController(QnaService qnaService) {
        this.qnaService = qnaService;
    }
    @ApiOperation(value="QnA 목록 조회", notes="QnA 문의 내역 조회")
    @GetMapping(value = "/qnaList") // -> 기존의 코코넛 호출 메서드명 : list - SystemQnaController, MemberQnaController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> qnaList(@RequestBody QnaSearchDto qnaSearchDto, Pageable pageable) {
        // TODO 토큰에서 받아온 권한 정보 확인. 권한에 따라서 내가 문의한 문의 내역 조회, 혹은 전체 문의 내역
        // 접속한 사용자 이메일
        String email = SecurityUtil.getCurrentUserEmail();
        return qnaService.qnaList(qnaSearchDto, pageable);
    }
    @ApiOperation(value="QnA 내용 조회", notes="QnA 문의 내용 조회")
    @GetMapping(value = "/qnaDetail/{idx}") // -> 기존의 코코넛 호출 메서드명 : detailView - SystemQnaController, MemberQnaController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> qnaDetail(@PathVariable("idx") Integer idx) {
        // TODO 토큰에서 받아온 권한 정보 확인
        // TODO 파일 저장 변경으로 인해 첨부 파일 조회 부분 변경 필요.
        return qnaService.qnaDetail(idx);
    }

    @ApiOperation(value="QnA 문의 등록", notes="QnA 문의 등록", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "/questionSave") // -> 기존의 코코넛 호출 메서드명 : writeView, save - MemberQnaController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> questionSave(@Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @Validated QnaQuestionSaveDto qnaQuestionSaveDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO 토큰에서 받아온 권한 정보 확인 (시스템 관리자는 질문 등록 못함)
        // 접속한 사용자 이메일
        String email = SecurityUtil.getCurrentUserEmail();
        return qnaService.questionSave(email, qnaQuestionSaveDto, request, response);
    }

    @ApiOperation(value="QnA 답변 등록", notes="QnA 답변 등록")
    @PostMapping(value = "/answerSave") // -> 기존의 코코넛 호출 메서드명 : answer - SystemQnaController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> answerSave(@RequestBody QnaAnswerSaveDto qnaAnswerSaveDto) throws IOException {
        // TODO 토큰에서 받아온 권한 정보 확인 (시스템 관리자만 답변 등록 가능)
        // 접속한 사용자 이메일
        String email = SecurityUtil.getCurrentUserEmail();
        return qnaService.answerSave(email, qnaAnswerSaveDto);
    }


}