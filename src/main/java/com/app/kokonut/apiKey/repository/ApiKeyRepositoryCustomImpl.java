package com.app.kokonut.apiKey.repository;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.entity.ApiKey;
import com.app.kokonut.apiKey.entity.QApiKey;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * @author Woody
 * Date : 2022-10-25
 * Time :
 * Remark :
 */
@Repository
public class ApiKeyRepositoryCustomImpl extends QuerydslRepositorySupport implements ApiKeyRepositoryCustom {

    public ApiKeyRepositoryCustomImpl() {
        super(ApiKey.class);
    }

    // 본사 접수현황 왼쪽 NativeQuery
//    @Override
//    public List<RequestReceiptListDto> findByHeadReceiptList(Long branchId, Long franchiseId, String filterFromDt, String filterToDt) {
//        EntityManager em = getEntityManager();
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("SELECT \n");
//        sb.append("d.br_id, d.br_name, c.fr_id, c.fr_name, a.fr_yyyymmdd, COUNT(*), SUM(b.fd_tot_amt) \n");
//        sb.append("FROM  fs_request a \n");
//        sb.append("INNER JOIN fs_request_dtl b on b.fr_id=a.fr_id \n");
//        sb.append("INNER JOIN bs_franchise c on c.fr_code=a.fr_code \n");
//        sb.append("INNER JOIN bs_branch d on d.br_code = c.br_code \n");
//        sb.append("WHERE \n");
//        sb.append("a.fr_confirm_yn='Y' \n");
//        sb.append("AND b.fd_cancel='N' \n");
//        sb.append("AND a.fr_yyyymmdd>= ?1 \n");
//        sb.append("AND a.fr_yyyymmdd<= ?2 \n");
//        if(branchId != 0){
//            sb.append("AND d.br_id = ?3 \n");
//            if(franchiseId != 0){
//                sb.append("AND c.fr_id = ?4 \n");
//            }
//            sb.append("GROUP BY d.br_name, c.fr_name, a.fr_yyyymmdd ORDER BY d.br_name, c.fr_name, a.fr_yyyymmdd ASC; \n");
//        }else{
//            sb.append("GROUP BY d.br_name, a.fr_yyyymmdd ORDER BY d.br_name, a.fr_yyyymmdd ASC; \n");
//        }
//
//        Query query = em.createNativeQuery(sb.toString());
//
//        query.setParameter(1, filterFromDt);
//        query.setParameter(2, filterToDt);
//        if(branchId != 0){
//            query.setParameter(3, branchId);
//            if(franchiseId != 0){
//                query.setParameter(4, franchiseId);
//            }
//        }
//
//        return jpaResultMapper.list(query, RequestReceiptListDto.class);
//    }

    // ApiKey 조회
    @Override
    public ApiKeyKeyDto findByKey(String key) {
        QApiKey apiKey = QApiKey.apiKey;

        JPQLQuery<ApiKeyKeyDto> query = from(apiKey)
                .where(apiKey.key.eq(key))
                .select(Projections.constructor(ApiKeyKeyDto.class,
                        apiKey.idx,
                        apiKey.companyIdx,
                        apiKey.adminIdx,
                        apiKey.registerName,
                        apiKey.key,
                        apiKey.regdate,
                        apiKey.type,
                        apiKey.note,

                        apiKey.validityStart,
                        apiKey.validityEnd,

                        apiKey.useAccumulate,
                        apiKey.state,
                        apiKey.useYn,

                        apiKey.reason,
                        apiKey.modifierIdx,
                        apiKey.modifierName,
                        apiKey.modifyDate
                ));

        return query.fetchOne();
    }

}
