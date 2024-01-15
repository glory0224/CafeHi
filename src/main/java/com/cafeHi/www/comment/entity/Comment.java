package com.cafeHi.www.comment.entity;

import com.cafeHi.www.qna.entity.QnA;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String comment;
    private String commentId;
    private String commentName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private QnA qna;

}
