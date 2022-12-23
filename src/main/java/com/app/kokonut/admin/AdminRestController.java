package com.app.kokonut.admin;

import com.app.kokonut.woody.common.AjaxResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-03
 * Time :
 * Remark :
 */
@Slf4j
@RequestMapping("/api/Admin")
@RestController
public class AdminRestController {

    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();

    private final AdminService adminService;

    @Autowired
    public AdminRestController(AdminService adminService){
        this.adminService=adminService;
    }

    @GetMapping("/authorityCheck")
    @ApiOperation(value = "JWT토큰 테스트" , notes = "JWT 토큰이 유효한지 테스트하는 메서드")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> authorityCheck() {
        return adminService.authorityCheck();
    }

    // 사업자 호출
    @GetMapping("/masterTest")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> masterTest() {
        log.info("ROLE_MASTER TEST");
        return ResponseEntity.ok(res.success(data));
    }

    // 관리자 호출
    @GetMapping("/adminTest")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> adminTest() {
        log.info("ROLE_ADMIN TEST");
        return ResponseEntity.ok(res.success(data));
    }

}
