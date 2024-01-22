package com.cafeHi.www.comment.repository;

import com.cafeHi.www.comment.entity.Comment;
import com.cafeHi.www.comment.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    // 저장
    public long save(Comment comment) {
        if(comment.getId() == null) {
            em.persist(comment);
        }
        return comment.getId();
    }

    // 특정 게시글에 대한 전체 댓글 데이터
    public List<Comment> findAll(Long qnaNum) {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    // 댓글 페이징
    public List<Comment> findPagingList(int limit, int offset) {
        QComment comment = QComment.comment1;

        return queryFactory
                .select(comment)
                .from(comment)
                .offset(offset -1)
                .limit(limit)
                .fetch();
    }

    // 특정 글에 대한 댓글 리스트
//    public List<Comment> findAllByQnANum(Comment comm) {
//        return em.createQuery("select c from Comment c where c.qna_id = : qnaId", Comment.class)
//                .getParameter("qnaId", qnaId)
//                .getResultList();
//    }

}
