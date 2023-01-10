package com.app.kokonutuser;

import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import com.app.kokonut.auth.jwt.util.SecurityUtil;
import com.app.kokonutuser.dtos.KokonutUserSearchDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-28
 * Time :
 * Remark : Kokonut-user RestController
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/DynamicUser")
public class DynamicUserRestController {

//	@Autowired
//	PersonalInfoService personalInfoService;
//
//	@Autowired
//	UserService userService;
//

	private final DynamicUserService dynamicUserService;
//
//	@Autowired
//	DynamicRemoveService dynamicRemoveService;
//
//	@Autowired
//	DynamicDormantService dynamicDormantService;
//
//	@Autowired
//	CompanyService companyService;
//
//	@Autowired
//	AwsKmsService awsKmsService;
//
//	@Autowired
//	ActivityHistoryService activityHistoryService;

	@Autowired
	public DynamicUserRestController(DynamicUserService dynamicUserService) {
		this.dynamicUserService = dynamicUserService;
	}

	// KokonutUserSerice 서비스 테스트용 API
	@GetMapping(value = "/serviceTest")
	public ResponseEntity<Map<String,Object>> serviceTest(@RequestParam(name="email", defaultValue = "") String email) {
		return dynamicUserService.serviceTest(email);
	}

	// 유저DB(테이블) 생성
	@PostMapping(value = "/createUserDatabase")
	@ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
	public ResponseEntity<Map<String,Object>> createUserDatabase() {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();

		log.info("jwtFilterDto.getEmail() : "+jwtFilterDto.getEmail());
		log.info("jwtFilterDto.getRole() : "+jwtFilterDto.getRole());

		return dynamicUserService.createTable(jwtFilterDto.getEmail());
	}

	// 유저DB(테이블) 리스트조회 -> 기존 코코넛 URL : /member/user/list
	@GetMapping(value = "/userListCall")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
	})
	public ResponseEntity<Map<String,Object>> userListCall(@RequestBody KokonutUserSearchDto kokonutUserSearchDto) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.userListCall(kokonutUserSearchDto, jwtFilterDto.getEmail());
	}

	// 유저생성(회원생성) -> 기존 코코넛 URL : /member/user/saveUser
	@PostMapping(value = "/userSaveCall")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
	})
	public ResponseEntity<Map<String,Object>> userSaveCall(@RequestBody HashMap<String,Object> paramMap) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.userSaveCall(paramMap, jwtFilterDto.getEmail());
	}

	// 유저정보 수정(회원수정) -> 기존 코코넛 URL : 없음
	@PostMapping(value = "/userUpdateCall")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
	})
	public ResponseEntity<Map<String,Object>> userUpdateCall(@RequestBody HashMap<String,Object> paramMap) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.userUpdateCall(paramMap, jwtFilterDto.getEmail());
	}

	// 유저삭제(회원삭제) -> 기존 코코넛 URL : 없음
	@PostMapping(value = "/userDeleteCall")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
	})
	public ResponseEntity<Map<String,Object>> userDeleteCall(@RequestParam(name="TYPE", defaultValue = "") String TYPE,
														   @RequestParam(name="IDX", defaultValue = "") Integer IDX) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.userDeleteCall(TYPE, IDX, jwtFilterDto.getEmail());
	}








}
