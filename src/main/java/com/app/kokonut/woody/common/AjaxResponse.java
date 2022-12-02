package com.app.kokonut.woody.common;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-10-21
 * Time :
 * Remark : Ajax 호출용 응답클래스
 */
public class AjaxResponse {

    Map<String, Object> res;

    public AjaxResponse(){
        this.res = new HashMap<>();
    }

    // 호출이 성공할시 해당함수 사용
    public Map<String, Object> success(HashMap<String,Object> sendData) {
        res.clear();
        res.put("sendData",sendData);
        res.put("status",200);
        res.put("timestamp", new Timestamp(System.currentTimeMillis()));
        res.put("message", "SUCCESS");
        res.put("err_code", "");
        res.put("err_msg", "");
        return this.res;
    }

    // 호출이 실패할시 해당함수 사용
    public Map<String, Object> fail(String err_code,String err_msg) {
        res.clear();
        res.put("status",500);
        res.put("timestamp", new Timestamp(System.currentTimeMillis()));
        res.put("message", "Error");
        res.put("err_code", err_code);
        res.put("err_msg", err_msg);
        return this.res;
    }

}
