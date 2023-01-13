package com.app.kokonut.totalDBDownload;

import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import com.app.kokonut.auth.jwt.util.SecurityUtil;
import com.app.kokonut.totalDBDownload.dtos.TotalDbDownloadSearchDto;
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
 * Date : 2023-01-13
 * Time :
 * Remark : 회원 DB 데이터 다운로드 요청 관련 API RestController
 */
@Slf4j
@RequestMapping("/api/TotalDbDownload")
@RestController
public class TotalDbDownloadRestController {

    private final TotalDbDownloadService totalDbDownloadService;

    @Autowired
    public TotalDbDownloadRestController(TotalDbDownloadService totalDbDownloadService){
        this.totalDbDownloadService = totalDbDownloadService;
    }

    // 개인정보 DB 데이터 전체 다운로드 요청 - 기존코코넛 메서드 : /member/totalDbDownload/apply
    @PostMapping(value = "/userDbDataDownloadApply")
    @ApiImplicitParams({
			@ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
			@ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String, Object>> userDbDataDownloadApply(@RequestParam(name="otpValue", defaultValue = "") String otpValue,
                                                                       @RequestParam(name="reason",required = false, defaultValue = "") String reason) {
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return totalDbDownloadService.userDbDataDownloadApply(otpValue, reason, jwtFilterDto.getEmail());
    }

    // 개인정보 DB 데이터 다운로드 요청건 리스트 - 기존코코넛 메서드 : /member/totalDbDownload/list
    @GetMapping(value = "/userDbDataDownloadList")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public ResponseEntity<Map<String, Object>> userDbDataDownloadList(@RequestBody TotalDbDownloadSearchDto totalDbDownloadSearchDto, Pageable pageable) {
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        return totalDbDownloadService.userDbDataDownloadList(totalDbDownloadSearchDto, jwtFilterDto.getEmail(), pageable);
    }

    // 개인정보 DB 데이터 다운로드 시작 - 기존코코넛 메서드 : /member/totalDbDownload/download
    @PostMapping(value = "/userDbDataDownloadStart")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="Bearer", value="JWT Token",required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name ="ApiKey", value="API Key",required = true, dataTypeClass = String.class, paramType = "header")
    })
    public void userDbDataDownloadStart(@RequestParam(name="idx", defaultValue = "") Integer idx) {
        JwtFilterDto jwtFilterDto = SecurityUtil.getCurrentJwt();
        totalDbDownloadService.userDbDataDownloadStart(idx, jwtFilterDto.getEmail());
    }

    /**
     *  전체DB 다운로드 요청 > 다운로드
     */
//    @SuppressWarnings("unchecked")
//    @RequestMapping(value = "/download", method = RequestMethod.POST)
//    public void download(@RequestParam  HashMap<String,Object> paramMap, HttpServletRequest request, HttpServletResponse response, @AuthorizedUser AuthUser authUser) throws IOException {
//
//        String email = authUser.getUser().getEmail();
//        if(email.equals("test@kokonut.me")){
//            System.out.println("체험하기모드는 할 수 없습니다.");
//        }
//        else {
//            String reason = "전체DB다운로드";
//            int userCompanyIdx = authUser.getUser().getCompanyIdx();
//            int adminIdx = authUser.getUser().getIdx();
//            String ipAddr = CommonUtil.clientIp();
//
//            request.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html; charset=UTF-8");
//            Map<String, String[]> reqMap = request.getParameterMap();
//
//            HashMap<String, Object> searchMap = new HashMap<String, Object>();
//            List<String> headerList = new ArrayList<String>();
//            List<String> headerKeyList = new ArrayList<String>();
//
//            /* 활동이력 추가 */
//            HashMap<String, Object> activityHistory = activityHistoryService.InsertActivityHistory(2, userCompanyIdx, adminIdx, 17, "", reason, ipAddr, 0);
//
//            try {
//                final String DECRYPTED_KEY = companyService.SelectCompanyEncryptKey(userCompanyIdx);
//                reqMap = new ObjectMapper().readValue(XssPreventer.unescape(paramMap.get("csv").toString()), HashMap.class);
//
//                int idx = Integer.parseInt(reqMap.get("idx").toString());
//                String companyName = reqMap.get("companyName").toString();
//
//                HashMap<String, Object> totalDbDownload = totalDbDownloadService.SelectTotalDbDownloadByIdx(idx);
//                int limit = Integer.parseInt(totalDbDownload.get("LIMIT").toString());
//                if (limit == 0) {
//                    throw new IOException("다운로드가 불가능합니다.");
//                }
//
//                if(companyName != null) companyName = new String(companyName.getBytes("8859_1"),"utf-8");
//
//                HashMap<String, Object> company = companyService.SelectCompanyByIdx(userCompanyIdx);
//                if(company != null) {
//                    int companyIdx = Integer.parseInt(company.get("IDX").toString());
//                    String tableName = dynamicUserService.SelectTableName(companyIdx);
//                    searchMap.put("tableName",  tableName);
//                }
//
//                String tableName = searchMap.get("tableName").toString();
//                List<HashMap<String, Object>> columns = dynamicUserService.SelectUserTable(tableName);
//
//                String sql = "SELECT * FROM `" + company.get("BUSINESS_NUMBER") + "` WHERE 1=1";
//                searchMap.put("sql", sql);
//
//                List<HashMap<String, Object>> userList = dynamicUserService.SelectUserList(searchMap);
//                List<HashMap<String, Object>> dormantList = dynamicDormantService.SelectDormantUserList(searchMap);
//                List<HashMap<String, Object>> removeList = dynamicRemoveService.SelectRemoveUserList(searchMap);
//
//                List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
//
//                boolean isDecryptMobileNumber = false;
//                for (int i = 0; i < columns.size(); i++) {
//                    HashMap<String, Object> userColumn = columns.get(i);
//                    String Field = userColumn.get("Field").toString();
//                    String Comment = userColumn.get("Comment").toString();
//
//                    // Field 옵션 명 및 암호화 정형화
//                    String FieldOptionName = Comment;
//
//                    if(Comment.contains("(")) {
//                        String[] FieldOptionNameList = Comment.split("\\(");
//                        FieldOptionName = FieldOptionNameList[0];
//                    }
//
//                    for (int j = 0; j < dataList.size(); j++) {
//                        HashMap<String, Object> map = dataList.get(j);
//
//                        // 내용이 비였으면 "" 값 입력
//                        if(!map.containsKey(Field)) {
//                            map.put(Field, "");
//                        }
//                    }
//                    if("성별".equals(FieldOptionName)) {
//                        FieldOptionName = "성별(0:남 / 1:여)";
//                    }
//                    if("개인정보 동의".equals(FieldOptionName)) {
//                        FieldOptionName = "개인정보 동의(N:동의하지않음 / Y:동의)";
//                    }
//                    // 휴대폰번호 암호화 분기처리된 값 처리를 위함
//                    if(Comment.contains("암호화") && Field.equals("Mobile_Number")) {
//                        isDecryptMobileNumber = true;
//                    }
//                    if("인덱스".equals(FieldOptionName)) continue;
//                    if("SALT".equals(FieldOptionName)) continue;
//
//                    headerList.add(FieldOptionName);
//                    headerKeyList.add(Field);
//                }
//
//
//                for (int i = 0; i < userList.size(); i++) {
//                    HashMap<String, Object> userMap = userList.get(i);
//                    dataList.add(userMap);
//                }
//                for (int i = 0; i < dormantList.size(); i++) {
//                    HashMap<String, Object> dormantrMap = dormantList.get(i);
//                    dataList.add(dormantrMap);
//                }
//                for (int i = 0; i < removeList.size(); i++) {
//                    HashMap<String, Object> removeMap = removeList.get(i);
//                    dataList.add(removeMap);
//                }
//
//                String exportFileName = tableName + "_회원DB전체데이터_" + LocalDate.now().toString() + ".csv";
//
//                String header = request.getHeader("User-Agent");
//                if (header.contains("MSIE") || header.contains("Trident")) {
//                    exportFileName = URLEncoder.encode(exportFileName,"UTF-8").replaceAll("\\+", "%20");
//                    response.setHeader("Content-Disposition", "attachment; filename=" + exportFileName + ";");
//                } else {
//                    exportFileName = new String(exportFileName.getBytes("UTF-8"), "ISO-8859-1");
//                    response.setHeader("Content-Disposition", "attachment; filename=\"" + exportFileName + "\"");
//                }
//
//                response.setContentType( "application/octet-stream; UTF-8" );
//                response.setHeader("Content-Type", "application/octet-stream");
//                response.setHeader("Content-Transfer-Encoding", "binary;");
//                response.setHeader("Pragma", "no-cache;");
//                response.setHeader("Expires", "-1;");
//
//                Cookie cookie = new Cookie("fileDownload", URLEncoder.encode("true", "UTF-8"));
//                cookie.setMaxAge(10);
//                response.addCookie(cookie);
//
//                PrintWriter out = response.getWriter();
//                out.write("\ufeff");
//                CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.EXCEL);
//
//                // 헤더
//                Object[] csvHeader = new String[headerList.size()];
//                for (int i = 0; i < headerList.size(); i++) {
//                    csvHeader[i] = headerList.get(i);
//                }
//                csvPrinter.printRecord(csvHeader);
//                out.flush();
//
//                // DATA
//                for (int i = 0; i < dataList.size(); i++) {
//                    HashMap<String, Object> map = dataList.get(i);
//                    Object[] csvBody = new String[headerKeyList.size()];
//                    for (int j = 0; j < headerKeyList.size(); j++) {
//                        String Field = headerKeyList.get(j).toString();
//                        if(map.containsKey(Field)) {
//                            // 핸드폰번호 강제 복호화
//                            if (isDecryptMobileNumber && Field.equals("Mobile_Number")) {
//                                String decryptedValue = AesCrypto.decrypt(new String(map.get(Field).toString().getBytes()), DECRYPTED_KEY);
//                                csvBody[j] = decryptedValue + "\t";
//                            } else {
//                                csvBody[j] = map.get(Field).toString() + "\t";
//                            }
//
//                            csvBody[j] = Utils.weekPointForExcel(csvBody[j].toString());
//                        } else {
//                            csvBody[j] = "";
//                        }
//                    }
//                    csvPrinter.printRecord(csvBody);
//                    out.flush();
//                }
//
//                csvPrinter.close();
//
//                // 다운로드 횟수 업데이트
//                int modifierIdx = authUser.getUser().getIdx();
//                String modifierName = authUser.getUsername();
//
//                HashMap<String, Object> updateMap = new HashMap<String, Object>();
//                updateMap.put("idx", idx);
//                updateMap.put("ipAddr", ipAddr);
//                updateMap.put("modifierIdx", modifierIdx);
//                updateMap.put("modifierName", modifierName);
//                updateMap.put("state", limit == 1 ? 3 : 2);
//                updateMap.put("limit", --limit);
//                totalDbDownloadService.UpdateTotalDbDownload(updateMap);
//
//                /* 활동이력 정상으로 변경 */
//                int activityHistoryIdx = Integer.parseInt(activityHistory.get("idx").toString());
//                activityHistoryService.UpdateActivityHistory(activityHistoryIdx, "", "", 1);
//
//                out.close();
//
//            } catch (IOException e) {
//                logger.error(e.getMessage());
//
//                /* 활동이력 사유 변경 */
//                String errorMsg = e.getClass().getSimpleName();
//                int activityHistoryIdx = Integer.parseInt(activityHistory.get("idx").toString());
//                activityHistoryService.UpdateActivityHistory(activityHistoryIdx, "", errorMsg, 0);
//            } catch (Exception e) {
//                logger.error(e.getMessage());
//
//                /* 활동이력 사유 변경 */
//                String errorMsg = e.getClass().getSimpleName();
//                int activityHistoryIdx = Integer.parseInt(activityHistory.get("idx").toString());
//                activityHistoryService.UpdateActivityHistory(activityHistoryIdx, "", errorMsg, 0);
//            }
//        }
//    }

}
