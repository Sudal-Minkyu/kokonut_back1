package com.app.kokonut.bizMessage.alimtalkTemplate;

import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateDeleteDto;
import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateSaveAndUpdateDto;
import com.app.kokonut.bizMessage.alimtalkTemplate.dto.AlimtalkTemplateSearchDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-15
 * Time :
 * Remark : 알림톡 템플릿 RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/AlimtalkTemplate")
public class AlimtalkTemplateRestController {

    private final AlimtalkTemplateService alimtalkTemplateService;

    @Autowired
    public AlimtalkTemplateRestController(AlimtalkTemplateService alimtalkTemplateService) {
        this.alimtalkTemplateService = alimtalkTemplateService;
    }

    // 알림톡 템플릿 리스트 조회 -> 수정작업이 들어가서 Post로 설정
    @PostMapping(value = "/alimTalkTemplateList")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> alimTalkTemplateList(@RequestBody AlimtalkTemplateSearchDto alimtalkTemplateSearchDto, Pageable pageable) throws Exception {
        return alimtalkTemplateService.alimTalkTemplateList(alimtalkTemplateSearchDto, pageable);
    }

    // 알림톡 템플릿 등록
    @PostMapping(value = "/saveAlimTalkTemplate")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> saveAlimTalkTemplate(@RequestBody AlimtalkTemplateSaveAndUpdateDto alimtalkTemplateSaveAndUpdateDto) {
        return alimtalkTemplateService.saveAlimTalkTemplate(alimtalkTemplateSaveAndUpdateDto);
    }

    // 알림톡 템플릿 수정
    @PostMapping(value = "/modifyAlimTalkTemplate")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> modifyAlimTalkTemplate(@RequestBody AlimtalkTemplateSaveAndUpdateDto alimtalkTemplateSaveAndUpdateDto) {
        return alimtalkTemplateService.modifyAlimTalkTemplate(alimtalkTemplateSaveAndUpdateDto);
    }

    // 알림톡 템플릿 삭제
    @PostMapping(value = "/deleteAlimTalkTemplates")
    @ApiImplicitParams({@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header")})
    public ResponseEntity<Map<String,Object>> deleteAlimTalkTemplates(@RequestBody AlimtalkTemplateDeleteDto alimtalkTemplateDeleteDto) {
        return alimtalkTemplateService.deleteAlimTalkTemplates(alimtalkTemplateDeleteDto);
    }

}
