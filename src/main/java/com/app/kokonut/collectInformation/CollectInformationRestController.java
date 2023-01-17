package com.app.kokonut.collectInformation;

import com.app.kokonut.auth.jwt.SecurityUtil;
import com.app.kokonut.collectInformation.dto.CollectInfoDetailDto;
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
@RequestMapping("v2/api/CollectInfomation")
public class CollectInformationRestController {
    /* 기존 컨트롤러
     * MemberCollectInformationControlle  "/member/collectInformation"
     *
     * 변경 컨트롤러
     * CollectInformationRestController    "/api/CollectInfomation"
     */

    private final CollectInformationService collectInformationService;

    public CollectInformationRestController(CollectInformationService collectInformationService) {
        this.collectInformationService = collectInformationService;
    }
    @ApiOperation(value="CollectInfo 목록 조회", notes="개인정보처리방침 목록 조회")
    @GetMapping(value = "/collectInfoList") // -> 기존의 코코넛 호출 메서드명 : list - MemberCollectInformationControlle
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> collectInfoList(@RequestBody CollectInfoSearchDto collectInfoSearchDto, Pageable pageable) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        String email = SecurityUtil.getCurrentJwt().getEmail();
        return collectInformationService.collectInfoList(userRole, email, collectInfoSearchDto, pageable);
    }

    @ApiOperation(value="CollectInfo 내용 조회", notes="개인정보처리방침 내용 조회")
    @GetMapping(value = "/collectInfoDetail") // -> 기존의 코코넛 호출 메서드명 : previewPopup - MemberCollectInformationControlle
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> collectInfoDetail(@RequestParam(name="idx") Integer idx) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        return collectInformationService.collectInfoDetail(userRole, idx);
    }

    @ApiOperation(value="CollectInfo 등록, 수정", notes="개인정보처리방침 수정, 등록")
    @PostMapping(value = "/collectInfoSave") // -> 기존의 코코넛 호출 메서드명 : save - MemberCollectInformationControlle
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> collectInfoSave(@RequestBody CollectInfoDetailDto collectInfoDetailDto) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        String email = SecurityUtil.getCurrentJwt().getEmail();
        return collectInformationService.collectInfoSave(userRole, email, collectInfoDetailDto);
    }

    @ApiOperation(value="CollectInfo 삭제", notes="개인정보처리방침 삭제")
    @PostMapping(value = "/collectInfoDelete") // -> 기존의 코코넛 호출 메서드명 : delete - MemberCollectInformationControlle
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String,Object>> collectInfoDelete(@RequestParam(name="idx") Integer idx) {
        String userRole = SecurityUtil.getCurrentJwt().getRole();
        String email = SecurityUtil.getCurrentJwt().getEmail();
        return collectInformationService.collectInfoDelete(userRole, email, idx);
    }

}
