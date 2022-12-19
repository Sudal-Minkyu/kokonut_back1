package com.app.kokonut.bizMessage.kakaoChannel;

import com.app.kokonut.bizMessage.kakaoChannel.dto.KakaoChannelSearchDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/KakaoChannel")
public class KakaoChannelRestController {

    private final KakaoChannelService kakaoChannelService;

    @Autowired
    public KakaoChannelRestController(KakaoChannelService kakaoChannelService) {
        this.kakaoChannelService = kakaoChannelService;
    }

    // 카카오 채널 조회 -> 수정작업이 들어가서 Post로 설정
    @PostMapping(value = "/kakaoTalkChannelList")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> kakaoTalkChannelList(@RequestBody KakaoChannelSearchDto kakaoChannelSearchDto, Pageable pageable) throws Exception {
        return kakaoChannelService.kakaoTalkChannelList(kakaoChannelSearchDto, pageable);
    }

    // 카카오톡 채널확인
    @GetMapping(value = "/postKakaoTalkChannels")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> postKakaoTalkChannels(@RequestParam(name="channelId", defaultValue = "") String channelId,
                                                                    @RequestParam(name="adminTelNo", defaultValue = "") String adminTelNo) throws Exception {
        return kakaoChannelService.postKakaoTalkChannels(channelId, adminTelNo);
    }

    // 본인이증 확인확인 + 카카오톡 채널등록
    @PostMapping(value = "/kakaoTalkchannelToken")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> kakaoTalkchannelToken(@RequestParam(name="channelId", defaultValue = "") String channelId,
                                                                    @RequestParam(name="token", defaultValue = "") String token) throws Exception {
        return kakaoChannelService.kakaoTalkchannelToken(channelId, token);
    }

    // 카카오톡 채널 삭제
    @PostMapping(value = "/deleteKakaoTalkChannels")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true,dataType="string",paramType = "header")})
    public ResponseEntity<Map<String,Object>> deleteKakaoTalkChannels(@RequestParam(name="channelId", defaultValue = "") String channelId) throws Exception {
        return kakaoChannelService.deleteKakaoTalkChannels(channelId);
    }


}
