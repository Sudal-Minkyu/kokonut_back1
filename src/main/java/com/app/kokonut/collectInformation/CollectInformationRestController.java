package com.app.kokonut.collectInformation;

import com.app.kokonut.auth.jwt.util.SecurityUtil;
import com.app.kokonut.collectInformation.dto.CollectInfoSearchDto;
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
 * Remark : 개인정보 처리방침 - 개인정보 수집 및 이용 안내 컨트롤러
 */
@Validated
@RestController
@RequestMapping("/api/CollectInfomation")
// AS-IS MemberCollectInformationControlle 리팩토링, url /member/collectInformation -> /api/CollectInfomation
public class CollectInformationRestController {

    private final CollectInformationService collectInformationService;

    public CollectInformationRestController(CollectInformationService collectInformationService) {
        this.collectInformationService = collectInformationService;
    }
    @ApiOperation(value="CollectInfo 목록 조회", notes="개인정보 처리방침 목록 조회")
    @GetMapping(value = "/collectInfoList") // -> 기존의 코코넛 호출 메서드명 : list - MemberCollectInformationControlle
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> collectInfoList(@RequestBody CollectInfoSearchDto collectInfoSearchDto, Pageable pageable) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        String email = SecurityUtil.getCurrentJwt().getEmail();
        return collectInformationService.collectInfoList(userRole, email, collectInfoSearchDto, pageable);
    }

    @ApiOperation(value="CollectInfo 내용 조회", notes="개인정보 처리방침 내용 조회")
    @GetMapping(value = "/collectInfoDetail/{idx}") // -> 기존의 코코넛 호출 메서드명 : detailView - SystemFaqController, FaqController
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> collectInfoDetail(@PathVariable("idx") Integer idx) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        return collectInformationService.detail();
    }

}
