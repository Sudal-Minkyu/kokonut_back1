package com.app.kokonut.collectInformation;

import com.app.kokonut.woody.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joy
 * Date : 2023-01-04
 * Time :
 * Remark : CollectInfomationService 개인정보 처리방침 서비스
 */
@Slf4j
@Service
public class CollectInformationService {

    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();

    private final CollectInformationRepository collectInfoRepository;
    public CollectInformationService(CollectInformationRepository collectInformationRepository, CollectInformationRepository collectInfoRepository) {
        this.collectInfoRepository = collectInfoRepository;
    }

    public ResponseEntity<Map<String, Object>> list() {
        log.info("list 호출");
        // 접속 정보에서 idx 가져오기
//        Admin admin = adminRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));
//        qnaSearchDto.setAdminIdx(admin.getIdx());

        //Page<QnaListDto> qnaListDtos = qnaRepository.findQnaPage(userRole, qnaSearchDto, pageable);

       // return ResponseEntity.ok(res.ResponseEntityPage(qnaListDtos));
        return null;
    }
    public ResponseEntity<Map<String,Object>> detail() {
        //log.info("");
        //data.put("qnaDetailDto",  qnaDetailDto);
        //return ResponseEntity.ok(res.success(data));
        //log.error("");
        //return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));

        return ResponseEntity.ok(res.success(data));
    }
}
