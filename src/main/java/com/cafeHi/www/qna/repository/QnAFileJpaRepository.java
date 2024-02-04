package com.cafeHi.www.qna.repository;

import com.cafeHi.www.qna.entity.QnA;
import com.cafeHi.www.qna.entity.QnAFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QnAFileJpaRepository extends JpaRepository<QnAFile, Long> {
    @Query("select qf from QnAFile qf where qf.qna  = :qnA")
    QnAFile findQnAFileByQnA(QnA qnA);
}
