package com.app.kokonut.service;
import com.app.kokonut.service.dto.ServiceDto;
import java.util.List;

/**
 * @author Joy
 * Date : 2023-01-05
 * Time :
 * Remark : 기존의 코코넛 프로젝트의 ServiceDao 쿼리호출
 */
public interface ServiceRepositoryCustom {

    // ServiceDao
    /**
     * 서비스(구독권) 목록 조회
     * AS-IS SelectServiceList
     **/
    List<ServiceDto> findServiceList();

    /**
     * 서비스(구독권) 상세 보기
     * AS-IS SelectServiceByIdx
     **/
    ServiceDto findServiceByIdx(Integer idx);

    /**
     * 서비스(구독권) 등록 하기
     * AS-IS InsertService
     **/

    /**
     * 서비스(구독권) 수정 하기
     * AS-IS UpdateService
     **/

    /**
     * 서비스(구독권) 삭제 하기
     * AS-IS DeleteServiceByIdx
     **/

    //public void InsertService(HashMap<String, Object> paramMap);
    //public void UpdateService(HashMap<String, Object> paramMap);
    //public void DeleteServiceByIdx(int idx);

}