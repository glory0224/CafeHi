package com.cafeHi.www.comment.repository;

import com.cafeHi.www.comment.entity.Comment;
import com.cafeHi.www.comment.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDslImpl implements CommentDsl{

    private final QComment comment = QComment.comment1;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findPagingList(int limit, int offset) {
        return queryFactory
                .select(comment)
                .from(comment)
                .offset(offset -1)
                .limit(limit)
                .fetch();
    }
}
