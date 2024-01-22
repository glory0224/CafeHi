package com.cafeHi.www.comment.service;

import com.cafeHi.www.comment.form.CommentForm;
import com.cafeHi.www.comment.entity.Comment;
import com.cafeHi.www.comment.repository.CommentRepository;
import com.cafeHi.www.qna.entity.QnA;
import com.cafeHi.www.qna.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final QnARepository qnARepository;

    @Transactional
    public void WriteComment(CommentForm commentForm) {
        QnA qnA = qnARepository.findQnA(Long.parseLong(commentForm.getQnaNum()));
        Comment comment = new Comment();
        comment.convertCommentForm(commentForm, qnA);
        commentRepository.save(comment);
    }

}
