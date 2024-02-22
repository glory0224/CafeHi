package com.cafeHi.www.comment.repository;

import com.cafeHi.www.comment.entity.Comment;

import java.util.List;

public interface CommentDsl {
    List<Comment> findPagingList(int limit, int offset);
}
