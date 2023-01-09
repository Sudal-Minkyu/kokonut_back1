//package com.app.kokonut.activity;
//
//import com.app.kokonut.activity.dto.ActivityBodyDto;
//import com.app.kokonut.activity.dto.ActivityListDto;
//import com.app.kokonut.activity.Activity;
//import com.app.kokonut.activity.ActivityService;
//import com.app.kokonut.woody.common.AjaxResponse;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author Woody
// * Date : 2022-11-03
// * Time :
// * Remark : Kokonut Activity RestController
// */
//@Slf4j
//@RestController
//@Secured("ROLE_SYSTEM")
//@RequestMapping("/api/activity")
//public class ActivityRestController {
//
//    private final ActivityService activityService;
//
//    @Autowired
//    public ActivityRestController(ActivityService activityService) {
//        this.activityService = activityService;
//    }
//
//    /***
//     * 시스템관리자 > 이력 관리 > 이력 보관기관 설정
//     */
////    @RequestMapping(value = "/activityManagement", method = RequestMethod.GET)
////    public ModelAndView activityManagement(){
////        ModelAndView mv = new ModelAndView("/System/Activity/ActivityManagementUI");
////
////        /* 고객정보처리 활동 리스트(type 1, type 3) */
////        Map<String, Object> paramMap = new HashMap<String, Object>();
////        paramMap.put("type", "1");
////        List<HashMap<String, Object>> personActivityList = activityService.SelectActivityList(paramMap);
////        mv.addObject("personActivityList", personActivityList);
////
////        /* 관리자 활동 리스트 */
////        paramMap.put("type", "4");
////        List<HashMap<String, Object>> adminActivityList = activityService.SelectActivityList(paramMap);
////        mv.addObject("adminActivityList", adminActivityList);
////
////        return mv;
////    }
//    /***
//     * 시스템관리자 > 이력 관리 > 이력 보관기관 설정
//     *
//     * parma : Integer type
//     */
//    @GetMapping("activityManagement")
//    @ApiOperation(value = "Activity 리스트 호출 API", notes = "" +
//            "시스템관리자 > 이력 관리 > 이력 보관기관 설정 > Activity 리스트")
//    public ResponseEntity<Map<String,Object>> activityManagement(@RequestParam(name="type") Integer type){
//
//        log.info("activityManagement 호출");
//        log.info("@RequestParam Integer type : "+type);
//
//        AjaxResponse res = new AjaxResponse();
//        HashMap<String, Object> data = new HashMap<>();
//
//        List<ActivityListDto> activityListDtoList = activityService.findByActivityTypeList(type);
//        log.info("activityDtoList : "+ activityListDtoList);
//
//        data.put("activityDtoList", activityListDtoList);
//
//        return ResponseEntity.ok(res.success(data));
//    }
//
////    /***
////     * 이력 보관기간 저장
////     * @param HashMap
////     * @return HashMap
////     */
////    @SuppressWarnings("unchecked")
////    @RequestMapping(value = "/save", method = RequestMethod.POST)
////    @ResponseBody
////    public HashMap<String, Object> save(@RequestBody HashMap<String,Object> paramMap) {
////        HashMap<String, Object> returnMap = new HashMap<String, Object>();
////        returnMap.put("isSuccess", "false");
////        returnMap.put("errorCode", "ERROR_UNKNOWN");
////
////        do {
////
////            List<HashMap<String, Object>> activityList = (List<HashMap<String, Object>>) paramMap.get("activityList");
////            activityService.SaveActivityList(activityList);
////
////            returnMap.put("isSuccess", "true");
////            returnMap.put("errorCode", "ERROR_SUCCESS");
////
////        } while(false);
////
////        return returnMap;
////    }
//
//    /***
//     * 이력 보관기간 저장
//     * @param List<ActivityBodyDto> activityBodyDto
//     * @return HashMap
//     * 일단 보류
//     */
//    @PostMapping("activitySave")
//    @ApiOperation(value = "Activity 리스트 호출 API", notes = "" +
//            "시스템관리자 > 이력 관리 > 이력 보관기관 설정 > Activity 리스트")
//    public ResponseEntity<Map<String,Object>> activitySave(@RequestBody List<ActivityBodyDto> activityBodyDto){
//
//        log.info("activitySave 호출");
//        log.info("@RequestBody ActivityBodyDto activityBodyDto : "+activityBodyDto);
//
//        AjaxResponse res = new AjaxResponse();
//        HashMap<String, Object> data = new HashMap<>();
//
//        List<Activity> saveActivityList = new ArrayList<>();
//        for(int i=0; i<activityBodyDto.size(); i++){
//            Activity activity = new Activity();
//            activity.setIdx(activityBodyDto.get(i).getIdx());
//            saveActivityList.add(activity);
//        }
//        activityService.saveActivityList(saveActivityList);
//        data.put("isSuccess", "true");
//        data.put("errorCode", "ERROR_SUCCESS");
//
//        return ResponseEntity.ok(res.success(data));
//    }
//
//
//
//}
