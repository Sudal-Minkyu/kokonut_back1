package com.app.kokonut.activityHistory;

import com.app.kokonut.activityHistory.dto.ActivityHistoryListDto;
import com.app.kokonut.activityHistory.dto.ActivityHistorySearchDto;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-11-03
 * Time :
 * Remark : Kokonut ActivityHistory RestController
 */
@Slf4j
@RestController
@RequestMapping("/v2/api/activityHistory")
public class ActivityHistoryRestController {

    private final ActivityHistoryService activityHistoryService;

    @Autowired
    public ActivityHistoryRestController(ActivityHistoryService activityHistoryService) {
        this.activityHistoryService = activityHistoryService;
    }

    // ActivityHistoryApiController -> 기존 코코넛의 kokonutApi 활동이력 조회 리펙토링
    // 기존 url : /api/activity-history , 변경 url : /api/activityHistory
    @GetMapping("/getList")
    @ApiOperation(value="활동이력 조회", notes="활동이력 목록을 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header", example = "jwtKey"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header", example = "apiKey")
    })
//    public ResponseEntity<GetListResponse> getList(
//            @ApiParam(value="유형 (1: 고객정보처리이력, 2: 관리자활동이력)", required=true, example="1") @RequestParam Integer type,
//            HttpServletRequest request) {
    public ResponseEntity<Map<String,Object>> getList(@ApiParam(value="유형 (1: 고객정보처리이력, 2: 관리자활동이력)", required=true, example="1") @RequestParam Integer type,
                                                       HttpServletRequest request) {

        log.info("ActivityHistory getList 리스트 호출");

        log.info("@RequestParam type : "+type);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        log.info("apiKeyInfo : "+apiKeyInfo);

//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        log.info("API_KEY_IDX : "+API_KEY_IDX);
//
//        final String IP = Utils.getClientIp(request);
//        log.info("IP : "+IP);

        // 조회 가능 타입: 고객정보처리, 관리자활동
        if(type != 1 && type != 2) {
            // 실패 리턴처리
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO002.getCode(), ResponseErrorCode.KO002.getDesc()));
        }

        ActivityHistorySearchDto activityHistorySearchDto = new ActivityHistorySearchDto();
        activityHistorySearchDto.setType(type);
//        activityHistorySearchDto.setcompanyId(apiKeyInfo.getCompanyId());
        activityHistorySearchDto.setcompanyId(2);

        List<ActivityHistoryListDto> activityHistoryListDtos = activityHistoryService.findByActivityHistoryList(activityHistorySearchDto);

        if(activityHistoryListDtos.size() == 0) {
            log.info("조회된 데이터가 없습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO003.getCode(), ResponseErrorCode.KO003.getDesc()));
        }

        log.info("activityHistoryListDtos : "+activityHistoryListDtos);
        data.put("activityHistoryListDtos", activityHistoryListDtos);

        return ResponseEntity.ok(res.success(data));
    }

//    @ApiOperation(value="활동이력 엑셀 다운로드", notes="활동이력 목록을 엑셀 파일로 다운로드", response=KokonutApiResponse.class)
//    @GetMapping("/excel/download")
//    public ResponseEntity<KokonutApiResponse> excelDownload(
//            @ApiParam(value="유형 (1: 고객정보처리이력, 2: 관리자활동이력)", required=true, example="1") @RequestParam Integer type,
//            HttpServletRequest request, HttpServletResponse response) {
//        HttpStatus status = HttpStatus.OK;
//        KokonutApiResponse body = new KokonutApiResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = CommonUtil.clientIp();
//        final String LOG_HEADER = "[kokonut api activity history excel download]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            // 조회 가능 타입: 고객정보처리이력, 관리자활동
//            if(type != 1 && type != 2) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("unknown type");
//            }
//
//            // 다운로드 이력을 남기기 위한 데이터 수집
//            List<String> fileNameToks = new ArrayList<String>();
//            String REASON = "";
//            switch(type) {
//                case 1:
//                    fileNameToks.add("고객정보처리이력");
//                    REASON = "고객정보처리 이력 엑셀 다운로드 API 요청";
//                    break;
//                case 2:
//                    fileNameToks.add("관리자활동이력");
//                    REASON = "관리자활동 이력 엑셀 다운로드 API 요청";
//                    break;
//            }
//
//            String fileName= excelService.generateFileName(fileNameToks);
//            final String TYPE = "API";
//            final Integer ADMIN_IDX = apiKeyInfo.getadminId();
//            final String REGISTER_NAME = apiKeyInfo.getRegisterName();
//
//            // 엑셀 다운로드
//            List<Map<String, Object>> list = activityHistoryService.SelectActivityHistoryList(type, apiKeyInfo.getCompanyId());
//            if(list == null) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found history list");
//            }
//
//            // 데이터 값 변환
//            for(Map<String, Object> item : list) {
//                Integer itemType = (Integer)item.get("TYPE");
//                item.put("TYPE", activityHistoryService.TYPE_LIST[itemType]);
//
//                if(item.containsKey("ACTIVITY")) {
//                    Integer itemActivity = (Integer)item.get("ACTIVITY");
//                    try {
//                        item.put("ACTIVITY", activityHistoryService.ACTIVITY_LIST[itemActivity]);
//                    } catch(ArrayIndexOutOfBoundsException e) {
//                        throw new Exception("unknown activity value: " + itemActivity.toString());
//                    }
//                }
//
//                if(item.containsKey("STATE")) {
//                    Integer itemState = (Integer)item.get("STATE");
//                    try {
//                        item.put("STATE", activityHistoryService.STATE_LIST[itemState]);
//                    } catch(ArrayIndexOutOfBoundsException e) {
//                        throw new Exception("unknown state value: " + itemState.toString());
//                    }
//                }
//            }
//
//            List<Column> columns = activityHistoryService.SelectColumns();
//
//            List<String> headerList = new ArrayList<String>();
//            List<List<String>> dataList = new ArrayList<List<String>>();
//
//            for(Column column : columns) {
//                String header = column.getField();
//                headerList.add(header);
//            }
//
//            for(Map<String, Object> item : list) {
//                List<String> data = new ArrayList<String>();
//
//                for(String header : headerList) {
//                    if(item.containsKey(header)) {
//                        data.add(item.get(header).toString());
//                    }
//                    else {
//                        data.add("");
//                    }
//                }
//
//                dataList.add(data);
//            }
//
//            if(fileName.isEmpty()) {
//                switch(type) {
//                    case 1:
//                        fileName = "personalinfohistorylist";
//                        break;
//                    case 2:
//                        fileName = "activityhistorylist";
//                        break;
//                    default:
//                        fileName = "historylist";
//                }
//            }
//
//            excelService.download(request, response, fileName, headerList, dataList);
//
//            // 다운로드 이력 저장
//            HashMap<String, Object> downloadHistoryMap = new HashMap<String, Object>();
//            downloadHistoryMap.put("type", TYPE);
//            downloadHistoryMap.put("fileName", fileName);
//            downloadHistoryMap.put("reason", REASON);
//            downloadHistoryMap.put("adminId", ADMIN_IDX);
//            downloadHistoryMap.put("registerName", REGISTER_NAME);
//
//            downloadHistoryService.InsertDownloadHistory(downloadHistoryMap);
//
//            // 관리자 활동이력 저장
//            activityHistoryService.InsertActivityHistory(type, apiKeyInfo.getCompanyId(), apiKeyInfo.getadminId(), type == 1 ? 6 : 7, "", REASON, IP, 1);
//
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<KokonutApiResponse>(body, status);
//    }







}
