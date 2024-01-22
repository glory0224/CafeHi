package com.cafeHi.www.qna.repository;

import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.entity.QMember;
import com.cafeHi.www.qna.form.QnASearch;
import com.cafeHi.www.qna.entity.QQnA;
import com.cafeHi.www.qna.entity.QQnAFile;
import com.cafeHi.www.qna.entity.QnA;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QnARepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public long save(QnA qna) {
        if (qna.getQnaNum() == null) {
            em.persist(qna);
        }
        return qna.getQnaNum();
    }

    public QnA findQnA(Long qnaNum) {
        return em.find(QnA.class, qnaNum);
    }

    public List<QnA> findAll(QnASearch qnASearch) {
        return em.createQuery("select q from QnA q", QnA.class)
                .getResultList();
    }

    public List<QnA> findPagingList(int limit, int offset, SearchCriteria searchCriteria){

        QQnA qnA = QQnA.qnA;
        QMember member = QMember.member;

        return queryFactory
                .select(qnA)
                .from(qnA)
                .join(qnA.member, member)
                /*.where(
                        searchCriteria.getSearchType().equals("title") ? qnA.qnaTitle.contains(searchCriteria.getKeyword()) :
                                searchCriteria.getSearchType().equals("content") ? qnA.qnaContent.contains(searchCriteria.getKeyword()) :
                                        searchCriteria.getSearchType().equals("writer") ? member.memberId.contains(searchCriteria.getKeyword()) :
                                                null
                )*/
                .where(searchDateFilter(searchCriteria, qnA).or(searchTypeFilter(searchCriteria,qnA,member)))
                .orderBy(qnA.qnaNum.desc())
                .offset(offset - 1)
                .limit(limit)
                .fetch();
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

   /* public List<QnA> findPagingList(int limit, int offset, SearchCriteria searchCriteria, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        QQnA qnA = QQnA.qnA;
        QMember member = QMember.member;

        BooleanExpression conditions = qnA.isNotNull();

        // 조건
        if (searchCriteria.getSearchType().equals("title")) {
            conditions = conditions.and(qnA.qnaTitle.contains(searchCriteria.getKeyword()));
        } else if (searchCriteria.getSearchType().equals("content")) {
            conditions = conditions.and(qnA.qnaContent.contains(searchCriteria.getKeyword()));
        } else if (searchCriteria.getSearchType().equals("writer")) {
            conditions = conditions.and(qnA.member.memberId.contains(searchCriteria.getKeyword()));
        }

        // 검색조건이 시작일보다 크거나 같고 종료일보다 작거나 같은 조건
        conditions = conditions.and(qnA.qnaWriteDate.goe(startDateTime).and(qnA.qnaWriteDate.loe(endDateTime)));

        // Fetch entities based on the conditions
        return queryFactory.selectFrom(qnA)
                .where(conditions)
                .orderBy(qnA.qnaNum.desc())
                .offset(offset - 1)
                .limit(limit)
                .fetch();
    }
*/

    public List<QnA> findQnAListByMemberCode(int limit, int offset, SearchCriteria searchCriteria, Long MemberCode){

        QQnA qnA = QQnA.qnA;
        QQnAFile qnAFile = QQnAFile.qnAFile;
        QMember member = QMember.member;

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

    public List<QnA> findQnAMemberCode(Long MemberCode) {
        QQnA qnA = QQnA.qnA;
        QQnAFile qnAFile = QQnAFile.qnAFile;
        QMember member = QMember.member;

        return queryFactory
                .select(qnA)
                .from(qnA)
                .join(qnA.member, member)
                .join(qnA.qnAFile, qnAFile)
                .where(member.id.eq(MemberCode))
                .fetch();
    }


    public int getCountAll() {
        return em.createQuery("select COUNT(q) from QnA q", Long.class)
                .getSingleResult()
                .intValue();
    }

    public int getPagingCount(SearchCriteria searchCriteria) {

        QQnA qnA = QQnA.qnA;
        QMember member = QMember.member;

        long totalCount  = queryFactory
                .select(qnA.qnaNum)
                .from(qnA)
                .join(qnA.member, member)
                .where(
                        searchCriteria.getSearchType().equals("title") ? qnA.qnaTitle.contains(searchCriteria.getKeyword()) :
                                searchCriteria.getSearchType().equals("content") ? qnA.qnaContent.contains(searchCriteria.getKeyword()) :
                                        searchCriteria.getSearchType().equals("writer") ? member.memberId.contains(searchCriteria.getKeyword()) :
                                                null
                )
                .fetchCount();

        return (int) totalCount;
    }

    public void deleteQnA(Long qnaNum) {
        QnA findQnA = em.find(QnA.class, qnaNum);

        if (findQnA != null) {
            em.remove(findQnA);
        }
    }


}
