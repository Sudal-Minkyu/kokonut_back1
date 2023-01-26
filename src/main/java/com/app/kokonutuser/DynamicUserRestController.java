package com.app.kokonutuser;

import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import com.app.kokonut.auth.jwt.SecurityUtil;
import com.app.kokonutuser.dtos.KokonutColumSaveDto;
import com.app.kokonutuser.dtos.KokonutColumUpdateDto;
import com.app.kokonutuser.dtos.KokonutUserSearchDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping(value = "/v2/api/DynamicUser")
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
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public ResponseEntity<Map<String,Object>> createUserDatabase() {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();

		log.info("jwtFilterDto.getEmail() : "+jwtFilterDto.getEmail());
		log.info("jwtFilterDto.getRole() : "+jwtFilterDto.getRole());

		return dynamicUserService.createTable(jwtFilterDto.getEmail());
	}

	// 유저DB(테이블) 리스트조회 -> 기존 코코넛 URL : /member/user/list
	@GetMapping(value = "/userListCall")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public ResponseEntity<Map<String,Object>> userListCall(@RequestBody KokonutUserSearchDto kokonutUserSearchDto) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.userListCall(kokonutUserSearchDto, jwtFilterDto.getEmail());
	}

	// 유저생성(회원생성) -> 기존 코코넛 URL : /member/user/saveUser
	@PostMapping(value = "/userSaveCall")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public ResponseEntity<Map<String,Object>> userSaveCall(@RequestBody HashMap<String,Object> paramMap) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.userSaveCall(paramMap, jwtFilterDto.getEmail());
	}

	// 유저정보 수정(회원수정) -> 기존 코코넛 URL : 없음
	@PostMapping(value = "/userUpdateCall")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public ResponseEntity<Map<String,Object>> userUpdateCall(@RequestBody HashMap<String,Object> paramMap) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.userUpdateCall(paramMap, jwtFilterDto.getEmail());
	}

	// 유저삭제(회원삭제) -> 기존 코코넛 URL : 없음
	@PostMapping(value = "/userDeleteCall")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public ResponseEntity<Map<String,Object>> userDeleteCall(@RequestParam(name="TYPE", defaultValue = "") String TYPE,
														   @RequestParam(name="IDX", defaultValue = "") Integer IDX) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.userDeleteCall(TYPE, IDX, jwtFilterDto.getEmail());
	}

	// 개인정보 일괄등록 - 엑셀파일 양식 다운로드 -> 기존 코코넛 URL : downloadExcelForm
	@GetMapping(value = "/downloadExcelForm")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public void downloadExcelForm(HttpServletRequest request, HttpServletResponse response) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		dynamicUserService.downloadExcelForm(request, response, jwtFilterDto.getEmail());
	}

	// 개인정보 일괄등록 - 엑셀파일 검사 -> 미리보여주기 기능 - 기존코코넛 메서드 : readUploadExcelFile #일단 보류 woody
	@PostMapping(value = "/readUploadExcelFile")
	@ApiImplicitParams({
//			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
//			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public ResponseEntity<Map<String, Object>> readUploadExcelFile(@RequestParam(name="type", defaultValue = "") String type,
																   MultipartHttpServletRequest request) {
//		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.readUploadExcelFile(request, type, "woody2@kokonut.me");
	}

	// 개인정보 테이블 필드 추가 - 기존코코넛 메서드 : /member/userDB/save
	@PostMapping(value = "/columSave")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public ResponseEntity<Map<String, Object>> columSave(@RequestBody KokonutColumSaveDto kokonutColumSaveDto) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.columSave(kokonutColumSaveDto, jwtFilterDto.getEmail());
	}

	// 개인정보 테이블 필드 수정 - 기존코코넛 메서드 : 없음
	@PostMapping(value = "/columUpdate")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public ResponseEntity<Map<String, Object>> columUpdate(@RequestBody KokonutColumUpdateDto kokonutColumUpdateDto) throws Exception {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.columUpdate(kokonutColumUpdateDto, jwtFilterDto.getEmail());
	}

	// 개인정보 테이블 필드 삭제 - 기존코코넛 메서드 : 없음
	@PostMapping(value = "/columDelete")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
	})
	public ResponseEntity<Map<String, Object>> columDelete(@RequestParam(name="fieldName", defaultValue = "") String fieldName) {
		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
		return dynamicUserService.columDelete(fieldName, jwtFilterDto.getEmail());
	}


}
