package com.app.kokonut.woody.restcontroller;

import com.app.kokonut.woody.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Woody
 * Date : 2022-10-24
 * Time :
 * Remark : Kokonut User RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

//    // 회원목록 조회
//    @GetMapping("companyDetail")
//    @ApiOperation(value = "회사 상세정보 호출 API", notes = "" +
//            "회사의 상세정보를 조회합니다.\n" +
//            "IDX에 '1'을 입력하시면 됩니다.")
//    public ResponseEntity<Map<String,Object>> companyDetail(@RequestParam(value="IDX") Integer IDX){
//        return userService.companyDetail(IDX);
//    }


}
