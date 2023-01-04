package com.app.kokonutremove;

import com.app.kokonutuser.DynamicUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Woody
 * Date : 2023-01-03
 * Time :
 * Remark : Kokonut-remove RestController
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/DynamicRemove")
public class DynamicRemoveRestController {

	private final DynamicRemoveService dynamicRemoveService;

	@Autowired
	public DynamicRemoveRestController(DynamicRemoveService dynamicRemoveService) {
		this.dynamicRemoveService = dynamicRemoveService;
	}

	// 회원DB 생성
//	@PostMapping(value = "/createUserDatabase")
//	@ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
//	public ResponseEntity<Map<String,Object>> createUserDatabase(@RequestParam(name="email", defaultValue = "") String email) {
//		JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();

//		log.info("jwtFilterDto.getEmail() : "+jwtFilterDto.getEmail());
//		log.info("jwtFilterDto.getRole() : "+jwtFilterDto.getRole());

//		return dynamicUserService.createTable(email);
//	}
	
	
}
