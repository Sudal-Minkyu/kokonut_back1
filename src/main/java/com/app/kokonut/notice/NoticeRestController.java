package com.app.kokonut.notice;

import com.app.kokonut.auth.jwt.util.SecurityUtil;
import com.app.kokonut.notice.dto.NoticeDetailDto;
import com.app.kokonut.notice.dto.NoticeSearchDto;
import com.app.kokonut.notice.dto.NoticeStateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "")
@Validated
@RestController
@RequestMapping("/api/Notice")
public class NoticeRestController {
    // 기존 코코넛 SystemNoticeController, NoticeController 컨트롤러 리팩토링
    // 기존 url : /system/notice , 변경 url : /api/Notice

    private final NoticeService noticeService;

    public NoticeRestController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    @ApiOperation(value="Notice 목록 조회", notes="공지사항 목록 조회")
    @GetMapping(value = "/noticeList") // -> 기존의 코코넛 호출 메서드명 : getList, list - NoticeController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> noticeList(@RequestBody NoticeSearchDto noticeSearchDto, Pageable pageable) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        return noticeService.noticeList(userRole, noticeSearchDto, pageable);
    }

    @ApiOperation(value="Notice 내용 조회", notes="공지사항 내용 조회")
    @GetMapping(value = "/noticeDetail/{idx}") // -> 기존의 코코넛 호출 메서드명 : detailView - SystemNoticeController, NoticeController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> noticeDetail(@PathVariable("idx") Integer idx) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        return noticeService.noticeDetail(userRole, idx);
    }

    @ApiOperation(value="Notice 등록, 수정", notes="공지사항 수정, 등록")
    @PostMapping(value = "/noticeSave") // -> 기존의 코코넛 호출 메서드명 : save - SystemNoticeController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> noticeSave(@RequestBody NoticeDetailDto noticeDetailDto) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        String email = SecurityUtil.getCurrentJwt().getEmail();
        return noticeService.noticeSave(userRole, email, noticeDetailDto);
    }

    @ApiOperation(value="Notice 삭제", notes="공지사항 삭제")
    @PostMapping(value = "/noticeDelete") // -> 기존의 코코넛 호출 메서드명 : delete - SystemNoticeController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> noticeDelete(@RequestParam(name="idx") Integer idx) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        String email = SecurityUtil.getCurrentJwt().getEmail();
        return noticeService.noticeDelete(userRole, email, idx);
    }

    @ApiOperation(value="Notice 게시 상태변경", notes="공지사항 게시 상태변경")
    @PostMapping(value = "/noticeState") // -> 기존의 코코넛 호출 메서드명 : updatePostingState, updateStopState - SystemNoticeController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> noticeState(@RequestBody NoticeStateDto noticeStateDto) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        String email = SecurityUtil.getCurrentJwt().getEmail();
        return noticeService.noticeState(userRole, email, noticeStateDto);
    }
}
