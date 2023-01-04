package com.app.kokonut.collectInformation;

import com.app.kokonut.auth.jwt.util.SecurityUtil;
import com.app.kokonut.faq.dto.FaqSearchDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : 개인정보 처리방침 컨트롤러
 */
@Validated
@RestController
@RequestMapping("/api/CollectInfomation")
// AS-IS MemberCollectInformationControlle 리팩토링
// AS-IS requestMapping url /member/collectInformation
public class CollectInformationRestController {

    private final CollectInformationService collectInformationService;

    public CollectInformationRestController(CollectInformationService collectInformationService) {
        this.collectInformationService = collectInformationService;
    }
    @ApiOperation(value="CollectInfo 목록 조회", notes="개인정보 처리방침 목록 조회")
    @GetMapping(value = "/collectInfoList") // -> 기존의 코코넛 호출 메서드명 : list - SystemFaqController, FaqController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> collectInfoList(@RequestBody FaqSearchDto faqSearchDto, Pageable pageable) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        return collectInformationService.list();
    }

    @ApiOperation(value="CollectInfo 내용 조회", notes="개인정보 처리방침 내용 조회")
    @GetMapping(value = "/collectInfoDetail/{idx}") // -> 기존의 코코넛 호출 메서드명 : detailView - SystemFaqController, FaqController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> collectInfoDetail(@PathVariable("idx") Integer idx) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        return collectInformationService.detail();
    }

}
