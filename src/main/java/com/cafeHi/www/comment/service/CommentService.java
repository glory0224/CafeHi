package com.cafeHi.www.comment.service;

import com.cafeHi.www.comment.dto.CommentForm;
import com.cafeHi.www.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void WriteComment(CommentForm commentForm) {

    }

}
