package com.app.kokonut.faq;

import com.app.kokonut.auth.jwt.SecurityUtil;
import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import com.app.kokonut.faq.dto.FaqDetailDto;
import com.app.kokonut.faq.dto.FaqSearchDto;
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
@RequestMapping("/v2/api/Faq")
public class FaqRestController {
    // 기존 코코넛 SystemFaqController 컨트롤러 리팩토링
    // 기존 url : /system/faq , 변경 url : /api/Faq

    // 기존 코코넛 FaqController 컨트롤러 리팩토링
    // 기존 url : /faq, 변경 url : /api/Faq

    private final FaqService faqService;

    public FaqRestController(FaqService faqService) {
        this.faqService = faqService;
    }

    @ApiOperation(value="Faq 목록 조회", notes="자주 묻는 질문 목록 조회")
    @GetMapping(value = "/faqList") // -> 기존의 코코넛 호출 메서드명 : list - SystemFaqController, FaqController
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> faqList(@RequestBody FaqSearchDto faqSearchDto, Pageable pageable) {
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return faqService.faqList(jwtFilterDto.getRole(), faqSearchDto, pageable);
    }

    @ApiOperation(value="Faq 내용 조회", notes="자주 묻는 질문 내용 조회")
    @GetMapping(value = "/faqDetail/{idx}") // -> 기존의 코코넛 호출 메서드명 : detailView - SystemFaqController, FaqController
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> faqDetail(@PathVariable("idx") Integer idx) {
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return faqService.faqDetail(jwtFilterDto.getRole(), idx);
    }

    @ApiOperation(value="Faq 등록, 수정", notes="자주 묻는 질문 등록, 수정")
    @PostMapping(value = "/faqSave") // -> 기존의 코코넛 호출 메서드명 : save - SystemFaqController
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> faqSave(@RequestBody FaqDetailDto faqDetailDto) {
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return faqService.faqSave(jwtFilterDto.getRole(), jwtFilterDto.getEmail(), faqDetailDto);
    }

    @ApiOperation(value="Faq 삭제", notes="자주 묻는 질문 삭제")
    @PostMapping(value = "/faqDelete") // -> 기존의 코코넛 호출 메서드명 : delete - SystemFaqController
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> faqDelete(@RequestParam(name="idx") Integer idx) {
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return faqService.faqDelete(jwtFilterDto.getRole(), jwtFilterDto.getEmail(), idx);
    }


}
