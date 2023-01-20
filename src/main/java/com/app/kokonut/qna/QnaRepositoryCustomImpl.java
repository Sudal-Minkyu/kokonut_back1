package com.app.kokonut.qna;

import com.app.kokonut.admin.entity.QAdmin;
import com.app.kokonut.admin.entity.enums.AuthorityRole;
import com.app.kokonut.qna.dtos.QnaDetailDto;
import com.app.kokonut.qna.dtos.QnaListDto;
import com.app.kokonut.qna.dtos.QnaSchedulerDto;
import com.app.kokonut.qna.dtos.QnaSearchDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public Page<QnaListDto> findQnaPage(String userRole, QnaSearchDto qnaSearchDto, Pageable pageable) {
        /*
         *
        SELECT A.`IDX`
             , A.`ADMIN_IDX`
			 , A.`TITLE`
			 , A.`TYPE`
			 , A.`REGDATE`
			 , A.`qnaState`
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
                .leftJoin(admin).on(admin.adminId.eq(qna.adminId))
                .select(Projections.constructor(QnaListDto.class,
                        qna.qnaId,
                        qna.adminId,
                        qna.qnaTitle,
                        qna.qnaType,
                        qna.insert_date,
                        qna.qnaState,
                        qna.modify_date,
                        admin.knName
                ));

        // 조건에 따른 where 절 추가
        if(qnaSearchDto.getQnaState() != null){
            query.where(qna.qnaState.eq(qnaSearchDto.getQnaState()));
        }
        if(qnaSearchDto.getQnaType() != null){
            query.where(qna.qnaType.eq(qnaSearchDto.getQnaType()));
        }
        if((qnaSearchDto.getAdminId() != null) &&(!AuthorityRole.ROLE_SYSTEM.getDesc().equals(userRole))){
            query.where(qna.adminId.eq(qnaSearchDto.getAdminId()));
        }
        if(qnaSearchDto.getStimeStart() != null){
            query.where(qna.insert_date.goe(qnaSearchDto.getStimeStart()));
        }
        if(qnaSearchDto.getStimeEnd() != null){
            query.where(qna.insert_date.loe(qnaSearchDto.getStimeEnd()));
        }

        query.orderBy(qna.insert_date.desc());

        final List<QnaListDto> QnaListDtos = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(QnaListDtos, pageable, query.fetchCount());
    }

    @Override
    public QnaDetailDto findQnaByIdx(Long qnaId) {
        /*
         *
        SELECT A.`IDX`
		     , A.`ADMIN_IDX`
			 , A.`TITLE`
			 , A.`CONTENT`
			 , A.`FILE_GROUP_ID`
			 , A.`TYPE`
			 , A.`REGDATE`
			 , A.`qnaState`
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
        QAdmin adminB = QAdmin.admin;   // 답변자 정보

        JPQLQuery<QnaDetailDto> query = from(qna)
                .select(Projections.constructor(QnaDetailDto.class,
                        qna.qnaId,
                        qna.adminId,
                        qna.qnaTitle,
                        qna.qnaContent,
                        qna.qnaType,
                        qna.insert_date,
                        qna.qnaState,
                        qna.qnaAnswer,
                        qna.modify_date,
                        adminQ.knEmail,
                        adminQ.knName.as("maskingName"),
                        adminB.knName.as("ansName")
                ));
        query.leftJoin(adminB).on(qna.adminId.eq(adminB.adminId)); // 답변자 이름을 구하기 위한 조인
        query.leftJoin(adminQ).on(qna.adminId.eq(adminQ.adminId)); // 질문자 이름을 구하기 위한 조인
        query.where(qna.qnaId.eq(qnaId));
        return query.fetchOne();
    }

    @Override
    public List<QnaSchedulerDto> findNoneAnswerQnaByRegDate(LocalDateTime compareDate) {
        QQna qna  = QQna.qna;
        JPQLQuery<QnaSchedulerDto> query = from(qna)
                .select(Projections.constructor(QnaSchedulerDto.class,
                        qna.qnaId,
                        qna.qnaTitle,
                        qna.insert_date));
        query.where(qna.insert_date.loe(compareDate),
                qna.qnaAnswer.isNull(),
                qna.insert_date.isNull());

        return query.fetch();
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
//                        qna.adminId,
//                        qna.title,
//                        qna.content,
//                        qna.fileGroupId,
//                        qna.type,
//                        qna.regdate,
//                        qna.qnaState,
//                        qna.answer,
//                        qna.answerDate,
//                        adminQ.email,
//                        adminQ.name.as("maskingName"),
//                        adminA.name.as("ansName")
//                ));
//        query.leftJoin(adminA).on(qna.adminId.eq(adminA.idx)); // 답변자 이름을 구하기 위한 조인
//        query.leftJoin(adminQ).on(qna.adminId.eq(adminQ.idx)); // 질문자 이름을 구하기 위한 조인
//        query.where(qna.idx.eq(idx));
//
//        return null;
//    }
}
