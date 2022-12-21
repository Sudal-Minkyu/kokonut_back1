package com.app.kokonut.bizMessage.friendtalkMessage;

import com.app.kokonut.bizMessage.friendtalkMessage.dto.FriendtalkMessageSearchDto;
import com.app.kokonut.bizMessage.friendtalkMessage.dto.FriendtalkMessageSendDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-20
 * Time :
 * Remark : FriendtalkMessage RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/FriendtalkMessage")
public class FriendtalkMessageRestController {

    private final FriendtalkMessageService friendtalkMessageService;

    @Autowired
    public FriendtalkMessageRestController(FriendtalkMessageService friendtalkMessageService) {
        this.friendtalkMessageService = friendtalkMessageService;
    }

    // 친구톡 메시지 리스트 조회
    @PostMapping(value = "/friendTalkMessageList")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> friendTalkMessageList(@RequestBody FriendtalkMessageSearchDto friendtalkMessageSearchDto, Pageable pageable) {
        return friendtalkMessageService.friendTalkMessageList(friendtalkMessageSearchDto, pageable);
    }

    // 친구톡 메시지 발송
    @PostMapping(value = "/postFriendMessages")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> postFriendMessages(@ModelAttribute FriendtalkMessageSendDto friendtalkMessageSendDto, HttpServletRequest request) {
        return friendtalkMessageService.postFriendMessages(friendtalkMessageSendDto, request);
    }



}
