package com.cafeHi.www.qna.repository;

import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.entity.QMember;
import com.cafeHi.www.qna.entity.QQnA;
import com.cafeHi.www.qna.entity.QQnAFile;
import com.cafeHi.www.qna.entity.QnA;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QnADslImpl implements QnADsl{

    private final JPAQueryFactory queryFactory;
    private final QQnA qnA = QQnA.qnA;
    private final QMember member = QMember.member;
    private final QQnAFile qnAFile = QQnAFile.qnAFile;

    @Override
    public List<QnA> findPagingList(int limit, int offset, SearchCriteria searchCriteria) {

//        QQnA qnA = QQnA.qnA;
//        QMember member = QMember.member;

        return queryFactory
                .select(qnA)
                .from(qnA)
                .where(searchDateFilter(searchCriteria, qnA).or(searchTypeFilter(searchCriteria,qnA,member)))
                .orderBy(qnA.qnaNum.desc())
                .offset(offset - 1)
                .limit(limit)
                .fetch();
    }

    @Override
    public List<QnA> findQnAListByMemberCode(int limit, int offset, SearchCriteria searchCriteria, Long MemberCode) {
//        QQnA qnA = QQnA.qnA;
//        QQnAFile qnAFile = QQnAFile.qnAFile;
//        QMember member = QMember.member;

        return queryFactory
                .select(qnA)
                .from(qnA)
                .join(qnA.member, member)
                .join(qnA.qnAFile, qnAFile)
                .where(member.id.eq(MemberCode).and(
                        searchCriteria.getSearchType().equals("title") ? qnA.qnaTitle.contains(searchCriteria.getKeyword()) :
                                searchCriteria.getSearchType().equals("content") ? qnA.qnaContent.contains(searchCriteria.getKeyword()) :
                                        searchCriteria.getSearchType().equals("writer") ? member.memberId.contains(searchCriteria.getKeyword()) :
                                                null
                ))
                .orderBy(qnA.qnaNum.desc())
                .offset(offset - 1)
                .limit(limit)
                .fetch();
    }

    @Override
    public int getPagingCount(SearchCriteria searchCriteria) {
//        QQnA qnA = QQnA.qnA;
//        QMember member = QMember.member;

        long totalCount  = queryFactory
                .select(qnA.qnaNum)
                .from(qnA)
                .where(
                        searchCriteria.getSearchType().equals("title") ? qnA.qnaTitle.contains(searchCriteria.getKeyword()) :
                                searchCriteria.getSearchType().equals("content") ? qnA.qnaContent.contains(searchCriteria.getKeyword()) :
                                        searchCriteria.getSearchType().equals("writer") ? member.memberId.contains(searchCriteria.getKeyword()) :
                                                null
                )
                .fetchCount();

        return (int) totalCount;
    }

    private BooleanExpression searchTypeFilter(SearchCriteria searchCriteria, QQnA qnA, QMember member) {
        return searchCriteria.getSearchType().equals("title") ? qnA.qnaTitle.contains(searchCriteria.getKeyword()) :
                searchCriteria.getSearchType().equals("content") ? qnA.qnaContent.contains(searchCriteria.getKeyword()) :
                        searchCriteria.getSearchType().equals("writer") ? member.memberId.contains(searchCriteria.getKeyword()) :
                                null;
    }


    // 날짜 필터
    private BooleanExpression searchDateFilter(SearchCriteria searchCriteria, QQnA qnA) {
        //goe, loe 사용
        BooleanExpression isGoeStartDate = qnA.qnaWriteDateTime.goe(LocalDateTime.of(searchCriteria.getStartDate(), LocalTime.MIN));
        BooleanExpression isLoeEndDate = qnA.qnaWriteDateTime.loe(LocalDateTime.of(searchCriteria.getEndDate(), LocalTime.MAX).withNano(0));
        return Expressions.allOf(isGoeStartDate, isLoeEndDate);
    }
}
