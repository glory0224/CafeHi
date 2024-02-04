package com.cafeHi.www.qna.repository;

import com.cafeHi.www.qna.entity.QnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface QnAJpaRepository extends JpaRepository<QnA, Long>, QnADsl {

}
