package com.cafeHi.www.comment.entity;

import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.qna.entity.QnA;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private QnA qna;

}
