package com.app.kokonutuser;

import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import com.app.kokonut.auth.jwt.util.SecurityUtil;
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

	/**
	 * 회원DB 생성 
	 */
	@PostMapping(value = "/createUserDatabase")
//	@ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
	public ResponseEntity<Map<String,Object>> createUserDatabase(@RequestParam(name="email", defaultValue = "") String email) {
//		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();

//		log.info("jwtFilterDto.getEmail() : "+jwtFilterDto.getEmail());
//		log.info("jwtFilterDto.getRole() : "+jwtFilterDto.getRole());

		return dynamicUserService.createTable(email);
	}
	
	
}
