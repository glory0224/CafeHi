package com.cafeHi.www.qna.repository;

import com.cafeHi.qna.entity.QnA;
import com.cafeHi.qna.entity.QnAFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QnAFileRepository {

    private final EntityManager em;

    public void save(QnAFile qnAFile) {
        if (qnAFile.getId() == null) {
            em.persist(qnAFile);
        }
    }

    public QnAFile findQnAFile(Long fileCode) {
        return em.find(QnAFile.class, fileCode);
    }

    public List<QnAFile> findAll() {
        return em.createQuery("select qf from QnAFile qf", QnAFile.class)
                .getResultList();
    }

    public QnAFile findQnAFileByQnA(QnA qnA) {
        return em.createQuery("select qf from QnAFile qf where qf.qna  = :qnA", QnAFile.class)
                .setParameter("qnA", qnA)
                .getSingleResult();

    }

    public void deleteQnAFile(Long QnAFileId) {
        QnAFile FindQnAFile = em.find(QnAFile.class, QnAFileId);

        if (FindQnAFile != null) {
            em.remove(FindQnAFile);
        }
    }


}
