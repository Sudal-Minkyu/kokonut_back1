package com.app.kokonut.qna;

import com.app.kokonut.admin.entity.QAdmin;
import com.app.kokonut.qna.dto.QnaDetailDto;
import com.app.kokonut.qna.dto.QnaListDto;
import com.app.kokonut.qna.dto.QnaSearchDto;
import com.app.kokonut.qna.entity.QQna;
import com.app.kokonut.qna.entity.Qna;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @author joy
 * Date : 2022-12-27
 * Time :
 * Remark : QnaRepositoryCustom 쿼리문 선언부
 */
@Repository
public class QnaRepositoryCustomImpl extends QuerydslRepositorySupport implements QnaRepositoryCustom {
    public final JpaResultMapper jpaResultMapper;

    public QnaRepositoryCustomImpl(JpaResultMapper jpaResultMapper) {
        super(Qna.class);
        this.jpaResultMapper = jpaResultMapper;
    }

    // qna 목록 조회
    @Override
    public Page<QnaListDto> findQnaPage(QnaSearchDto qnaSearchDto, Pageable pageable) {
        /*
         *
        SELECT A.`IDX`
             , A.`ADMIN_IDX`
			 , A.`TITLE`
			 , A.`TYPE`
			 , A.`REGDATE`
			 , A.`STATE`
			 , A.`ANSWER_DATE`
			 , B.`NAME` AS `MASKING_NAME` -- 김**개, 홍*동, 최*선
		FROM `qna` A
   LEFT JOIN `admin` B
		  ON A.`ADMIN_IDX` = B.`IDX`
		WHERE 1 = 1
	            ...
		ORDER BY A.`REGDATE` DESC;
		*
        */
        QQna qna  = QQna.qna;
        QAdmin admin  = QAdmin.admin;

        JPQLQuery<QnaListDto> query = from(qna)
                .leftJoin(admin).on(admin.idx.eq(qna.adminIdx))
                .select(Projections.constructor(QnaListDto.class,
                        qna.idx,
                        qna.adminIdx,
                        qna.title,
                        qna.type,
                        qna.regdate,
                        qna.state,
                        qna.answerDate,
                        admin.name
                ));

        // 조건에 따른 where 절 추가
        if(qnaSearchDto.getState() != null){
            query.where(qna.state.eq(qnaSearchDto.getState()));
        }
        if(qnaSearchDto.getType() != null){
            query.where(qna.type.eq(qnaSearchDto.getType()));
        }
        if(qnaSearchDto.getAdminIdx() != null){
            query.where(qna.adminIdx.eq(qnaSearchDto.getAdminIdx()));
        }
        if(qnaSearchDto.getStimeStart() != null){
            query.where(qna.regdate.goe(qnaSearchDto.getStimeStart()));
        }
        if(qnaSearchDto.getStimeEnd() != null){
            query.where(qna.regdate.loe(qnaSearchDto.getStimeEnd()));
        }

        // AS-IS
        // 오늘, 최근 일주일, 최근 한달 ... 등 화면단에서 기간을 정해서 dateType으로 넘긴 다음,
        // 해당 dateType에 따른 기간을 where 조건으로 추가함. 조회일자를 가져오는 부분이 있기 때문에 해당 부분은 필요하지 않다고 판단하여 구현하지 않음.
        // 추후 프론트에서 넘기더라도 타입에 따라서 화면에서 시작일자 종료일자를 넘기는 형태로 구현 가능

        query.orderBy(qna.regdate.desc());

        final List<QnaListDto> QnaListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(QnaListDtos, pageable, query.fetchCount());
    }

    @Override
    public QnaDetailDto findQnaByIdx(Integer idx) {
        /*
         *
        SELECT A.`IDX`
		     , A.`ADMIN_IDX`
			 , A.`TITLE`
			 , A.`CONTENT`
			 , A.`FILE_GROUP_ID`
			 , A.`TYPE`
			 , A.`REGDATE`
			 , A.`STATE`
			 , A.`ANS_IDX`
			 , A.`ANSWER`
			 , A.`ANSWER_DATE`
			 , B.`NAME` AS `MASKING_NAME`
             , B.`EMAIL`
			 , C.`NAME` AS `ANS_NAME`
		 FROM `qna` A
    LEFT JOIN `admin` B
		   ON A.`ADMIN_IDX` = B.`IDX`
    LEFT JOIN `admin` C
	 	   ON A.`ANS_IDX` = C.`IDX`
	    WHERE A.`IDX` = #{idx}
        *
        */

        QQna qna  = QQna.qna;
        QAdmin adminQ  = QAdmin.admin;  // 질문자 정보
        QAdmin adminA = QAdmin.admin;   // 답변자 정보

        JPQLQuery<QnaDetailDto> query = from(qna)
                .select(Projections.constructor(QnaDetailDto.class,
                        qna.idx,
                        qna.adminIdx,
                        qna.title,
                        qna.content,
                        qna.fileGroupId,
                        qna.type,
                        qna.regdate,
                        qna.state,
                        qna.answer,
                        qna.answerDate,
                        adminQ.email,
                        adminQ.name.as("maskingName"),
                        adminA.name.as("ansName")
                ));
        query.leftJoin(adminA).on(qna.adminIdx.eq(adminA.idx)); // 답변자 이름을 구하기 위한 조인
        query.leftJoin(adminQ).on(qna.adminIdx.eq(adminQ.idx)); // 질문자 이름을 구하기 위한 조인
        query.where(qna.idx.eq(idx));
        return query.fetchOne();
    }

//    @Override
//    public QnaAnswerSaveDto saveQnaAnswerByIdx(QnaAnswerSaveDto qnaAnswerSaveDto) {
//        QQna qna  = QQna.qna;
//        JP
//        JPQLQuery<QnaAnswerSaveDto> query =
//                update()
//                from(qna)
//
//                .select(Projections.constructor(QnaAnswerSaveDto.class,
//                        qna.idx,
//                        qna.adminIdx,
//                        qna.title,
//                        qna.content,
//                        qna.fileGroupId,
//                        qna.type,
//                        qna.regdate,
//                        qna.state,
//                        qna.answer,
//                        qna.answerDate,
//                        adminQ.email,
//                        adminQ.name.as("maskingName"),
//                        adminA.name.as("ansName")
//                ));
//        query.leftJoin(adminA).on(qna.adminIdx.eq(adminA.idx)); // 답변자 이름을 구하기 위한 조인
//        query.leftJoin(adminQ).on(qna.adminIdx.eq(adminQ.idx)); // 질문자 이름을 구하기 위한 조인
//        query.where(qna.idx.eq(idx));
//
//        return null;
//    }
}
