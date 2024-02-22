package com.cafeHi.www.comment.repository;

import com.cafeHi.www.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long>, CommentDsl {

}
