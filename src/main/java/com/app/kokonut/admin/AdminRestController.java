package com.app.kokonut.admin;

import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import com.app.kokonut.auth.jwt.SecurityUtil;
import com.app.kokonut.common.AjaxResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/v1/api/Admin")
@RestController
public class AdminRestController {

    private final AdminService adminService;

    @Autowired
    public AdminRestController(AdminService adminService){
        this.adminService=adminService;
    }

    @GetMapping("/authorityCheck")
    @ApiOperation(value = "JWT토큰 테스트" , notes = "JWT 토큰이 유효한지 테스트하는 메서드")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Authorization", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
    })
    public ResponseEntity<Map<String,Object>> authorityCheck() {
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return adminService.authorityCheck(jwtFilterDto);
    }

    // 시스템관리자 호출
    @GetMapping("/systemTest")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="authorization", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
    })
    public ResponseEntity<Map<String,Object>> systemTest() {
        log.info("ROLE_SYSTEM TEST");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return adminService.authorityCheck(jwtFilterDto);
    }

    // 대표관리자 호출
    @GetMapping("/masterTest")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="authorization", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
    })
    public ResponseEntity<Map<String,Object>> masterTest() {
        log.info("ROLE_MASTER TEST");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return adminService.authorityCheck(jwtFilterDto);
    }

    // 최고관리자 호출
    @GetMapping("/adminTest")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Authorization", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
    })
    public ResponseEntity<Map<String,Object>> adminTest() {
        log.info("ROLE_ADMIN TEST");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return adminService.authorityCheck(jwtFilterDto);
    }

    // 일반관리자 호출
    @GetMapping("/userTest")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Authorization", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
    })
    public ResponseEntity<Map<String,Object>> userTest() {
        log.info("ROLE_USER TEST");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return adminService.authorityCheck(jwtFilterDto);
    }

    // 게스트 호출
    @GetMapping("/guestTest")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Authorization", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
    })
    public ResponseEntity<Map<String,Object>> guestTest() {
        log.info("ROLE_GUEST TEST");
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return adminService.authorityCheck(jwtFilterDto);
    }


}
