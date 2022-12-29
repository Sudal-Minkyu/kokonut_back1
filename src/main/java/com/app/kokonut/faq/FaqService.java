package com.app.kokonut.faq;

import org.springframework.stereotype.Service;

@Service
public class FaqService {
    private final FaqRepository faqRepository;
    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

//    기존 코코넛 컨트롤러
//    // 시스템 관리자 > 섹션 관리 > 자주하는 질문
//    @RequestMapping(value = "/faqManagement", method = RequestMethod.GET)
//    public ModelAndView faqManagement(){
//        ModelAndView mv = new ModelAndView("/System/Faq/FaqManagementUI");
//        return mv;
//    }
//
//    // 시스템 관리자 > 섹션 관리 >  자주하는 질문 > 상세보기 페이지
//    @RequestMapping(value = "/detailView/{idx}", method = RequestMethod.GET)
//    public ModelAndView detailView(@PathVariable Integer idx){
//        ModelAndView mv = new ModelAndView("/System/Faq/DetailView");
//
//        HashMap<String, Object> faq = faqService.SelectFaqByIdx(idx);
//        mv.addObject("faq", faq);
//
//        return mv;
//    }
//
//    // 시스템 관리자 > 섹션 관리 > 자주하는 질문 > FAQ 등록
//    @RequestMapping(value = "/writeView", method = RequestMethod.GET)
//    public ModelAndView writeView(@RequestParam(value="id", required = false) Integer idx){
//        ModelAndView mv = new ModelAndView("/System/Faq/WriteView");
//
//        if(idx != null) {
//            HashMap<String, Object> faq = faqService.SelectFaqByIdx(idx);
//            mv.addObject("faq", faq);
//        }
//
//        return mv;
//    }
//
//    // 시스템 관리자 > 섹션 관리 > 자주하는 질문 > 자주하는 질문 저장
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @ResponseBody
//    public HashMap<String, Object> save(@RequestBody HashMap<String,Object> paramMap, @AuthorizedUser AuthUser authUser) {
//        HashMap<String, Object> returnMap = new HashMap<String, Object>();
//        returnMap.put("isSuccess", "false");
//        returnMap.put("errorCode", "ERROR_UNKNOWN");
//
//        do {
//
//            if(!paramMap.containsKey("idx")) {
//                returnMap.put("errorCode", "ERROR_NOT_FOUND_IDX");
//                break;
//            }
//
//            String answer = paramMap.get("answer").toString();
//            paramMap.put("answer", XssPreventer.escape(answer));
//
//            String idx = paramMap.get("idx").toString();
//            int userIdx = authUser.getUser().getIdx();
//            String userName = authUser.getUser().getName();
//            if("".equals(idx)) {
//                paramMap.put("adminIdx", userIdx);
//                paramMap.put("registerName", userName);
//
//                faqService.InsertFaq(paramMap);
//                idx = paramMap.get("idx").toString();
//            } else {
//                paramMap.put("modifierIdx", userIdx);
//                paramMap.put("modifierName", userName);
//
//                faqService.UpdateFaq(paramMap);
//            }
//
//            returnMap.put("idx", idx);
//            returnMap.put("isSuccess", "true");
//            returnMap.put("errorCode", "ERROR_SUCCESS");
//
//        } while(false);
//
//        return returnMap;
//    }
//
//    // 시스템 관리자 > 섹션 관리 > 자주하는 질문 > 삭제
//    @RequestMapping(value = "/delete", method = RequestMethod.POST)
//    @ResponseBody
//    public HashMap<String, Object> delete(@RequestBody HashMap<String,Object> paramMap, @AuthorizedUser AuthUser authUser) {
//        HashMap<String, Object> returnMap = new HashMap<String, Object>();
//        returnMap.put("isSuccess", "false");
//        returnMap.put("errorCode", "ERROR_UNKNOWN");
//
//        do {
//
//            if(!paramMap.containsKey("idx")) {
//                returnMap.put("errorCode", "ERROR_NOT_FOUND_IDX");
//                break;
//            }
//
//            String idx = paramMap.get("idx").toString();
//            faqService.DeleteFaqByIdx(Integer.parseInt(idx));
//
//            returnMap.put("isSuccess", "true");
//            returnMap.put("errorCode", "ERROR_SUCCESS");
//
//        } while(false);
//
//        return returnMap;
//    }
//
//    // 자주하는 질문 리스트
//    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    @ResponseBody
//    public String list(@RequestBody HashMap<String,Object> paramMap) {
//
//        int total = 0;
//        List<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
//
//        DataTables dataTables = null;
//
//        try{
//            HashMap<String, Object> searchMap = null;
//            if(paramMap.containsKey("searchData")){
//                searchMap = (HashMap<String, Object>) paramMap.get("searchData");
//                paramMap.putAll(searchMap);
//            }
//
//            rows = faqService.SelectFaqList(paramMap);
//            total = faqService.SelectFaqListCount(paramMap);
//
//            dataTables = new DataTables(paramMap, rows, total);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//
//        return dataTables.getJsonString();
//    }

//    // 고객지원 자주하는 질문
//    @RequestMapping(value = "/faqList", method = RequestMethod.GET)
//    public ModelAndView faqList(){
//        ModelAndView mv = new ModelAndView("/WebFront/FAQListUI");
//
//        return mv;
//    }
//
//    // 자주하는 질문 리스트 @param currentPage - 현재 페이지 @param listCount - 출력할 리스트 개수
//    @RequestMapping(value = "/list", method = RequestMethod.POST)
//    @ResponseBody
//    public HashMap<String, Object> list(@RequestBody HashMap<String,Object> paramMap) {
//        HashMap<String, Object> returnMap = new HashMap<String, Object>();
//        returnMap.put("isSuccess", "false");
//        returnMap.put("errorCode", "ERROR_UNKNOWN");
//
//        do {
//
//            if(!paramMap.containsKey("currentPage")) {
//                returnMap.put("errorCode", "NOT_FOUND_CURRENT_PAGE");
//                break;
//            }
//            if(!paramMap.containsKey("listCount")) {
//                returnMap.put("errorCode", "NOT_FOUND_LIST_COUNT");
//                break;
//            }
//
//            paramMap.put("state", "1");
//            int currentPage = Integer.parseInt(paramMap.get("currentPage").toString());
//            int listCount = Integer.parseInt(paramMap.get("listCount").toString());
//            int pageCount = 5;
//            int totalCount = faqService.SelectFaqListCount(paramMap);
//
//            int rowStartNumber = (currentPage - 1) * listCount;
//            int rowEndNumber = currentPage * listCount;
//
//            PageNavigator pageNavigator = new PageNavigator(currentPage, pageCount, listCount, totalCount);
//
//            paramMap.put("rowStartNumber", rowStartNumber);
//            paramMap.put("rowEndNumber", rowEndNumber);
//
//            List<HashMap<String, Object>> list = faqService.SelectFaqList(paramMap);
//            for (HashMap<String, Object> faq : list) {
//                if(faq.containsKey("ANSWER"))
//                    faq.put("ANSWER", XssPreventer.unescape(faq.get("ANSWER").toString()));
//            }
//
//            returnMap.put("list", list);
//            returnMap.put("pageNavigator", pageNavigator.getMakePage());
//            returnMap.put("totalPage", pageNavigator.getTotalNumOfPage());
//            returnMap.put("totalCount", totalCount);
//            returnMap.put("isSuccess", "true");
//            returnMap.put("errorCode", "ERROR_SUCCESS");
//
//        } while(false);
//
//        return returnMap;
//    }
//
//    // 섹션 > FAQ 리스트
//    @RequestMapping(value = "/faqList", method = RequestMethod.POST)
//    @ResponseBody
//    public String faqList(@RequestBody HashMap<String,Object> paramMap) {
//        int total = 0;
//        List<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
//        DataTables dataTables = null;
//        try{
//            HashMap<String, Object> searchMap = null;
//            if(paramMap.containsKey("searchData")){
//                searchMap = (HashMap<String, Object>) paramMap.get("searchData");
//                paramMap.putAll(searchMap);
//            }
//            rows = faqService.SelectFaqList(paramMap);
//            total = faqService.SelectFaqListCount(paramMap);
//
//            dataTables = new DataTables(paramMap, rows, total);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return dataTables.getJsonString();
//    }


//    기존 코코넛 서비스 목록
//    Faq 리스트 - public List<HashMap<String, Object>> SelectFaqList(HashMap<String, Object> paramMap) { return dao.SelectFaqList(paramMap);}
//    Faq 리스트 Count - public int SelectFaqListCount(HashMap<String, Object> paramMap) { return dao.SelectFaqListCount(paramMap);}
//    Faq 상세보기 - public HashMap<String, Object> SelectFaqByIdx(int idx) { return dao.SelectFaqByIdx(idx);}
//    Faq insert - public void InsertFaq(HashMap<String, Object> paramMap) { dao.InsertFaq(paramMap);}
//    Faq update - public void UpdateFaq(HashMap<String, Object> paramMap) { dao.UpdateFaq(paramMap);}
//    Faq 삭제 - public void DeleteFaqByIdx(int idx) { dao.DeleteFaqByIdx(idx);}
//    Faq 조회수 update - public void UpdateFaqViewCount(HashMap<String, Object> paramMap) {dao.UpdateFaqViewCount(paramMap);}

}
