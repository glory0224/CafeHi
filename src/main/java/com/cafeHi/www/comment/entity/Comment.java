package com.cafeHi.www.comment.entity;

import com.cafeHi.www.comment.dto.CommentDTO;
import com.cafeHi.www.comment.form.CommentForm;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.qna.entity.QnA;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private QnA qna;
    @CreatedDate
    private LocalDateTime commentWriteDateTime;
    @LastModifiedDate
    private LocalDateTime commentUpdateDateTime;

    public void convertCommentForm(CommentForm commentForm, QnA qna) {
        this.comment = commentForm.getComment();
//        this.commentWriteDateTime = commentForm.getWriteDateTime();
//        this.commentUpdateDateTime = commentForm.getUpdateDateTime();
        this.member = qna.getMember();
        this.qna = qna;
    }

    public CommentDTO convertCommentDTO() {
        CommentDTO commentDTO = new CommentDTO(this);
        return commentDTO;
    }

}
