package com.cafeHi.www.qna.entity;

import com.cafeHi.www.comment.entity.Comment;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.qna.dto.QnAForm;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
//@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class QnA {

    // 게시글 번호 사용을 위해 시퀀스 전략 사용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@SequenceGenerator(name = "seq_jpa_qna", sequenceName = "seq_jpa_qna", allocationSize = 1) // Oracle 시퀀스 적용
    @Column(name = "qna_id")
    private Long qnaNum;
    private String qnaTitle;
    private String qnaTitleClassification;
    private String qnaContent;
    @CreatedDate
    private LocalDateTime qnaWriteDateTime;
    @LastModifiedDate
    private LocalDateTime qnaUpdateDateTime;
    private int qnaHit;

    @OneToOne(mappedBy = "qna", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private QnAFile qnAFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private Member member;

    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Comment> comments;



    public void WriteFormSetQnA(QnAForm qnAForm) {
        this.qnaTitle = qnAForm.getQnaTitle();
        this.qnaTitleClassification = qnAForm.getQnaTitleClassification();
        this.qnaContent = qnAForm.getQnaContent();
        this.qnaHit = qnAForm.getQnaHit();
//        this.qnaWriteDateTime = LocalDateTime.now();
//        this.qnaUpdateDateTime = LocalDateTime.now();
    }

    public void MemberSetQnA(Member member) {
        this.member = member;
    }


    public void increaseHit() {
        this.qnaHit = qnaHit + 1;
    }


    public void updateFormSetQnA(QnAForm qnAForm) {
        this.qnaTitle = qnAForm.getQnaTitle();
        this.qnaTitleClassification = qnAForm.getQnaTitleClassification();
        this.qnaContent = qnAForm.getQnaContent();
        this.qnaHit = qnAForm.getQnaHit();
//        this.qnaUpdateDateTime = LocalDateTime.now();
    }



}
