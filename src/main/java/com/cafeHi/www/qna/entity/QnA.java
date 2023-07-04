package com.cafeHi.www.qna.entity;

import com.cafeHi.member.entity.Member;
import com.cafeHi.qna.dto.QnAForm;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
//@Setter
@ToString
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
    private LocalDateTime qnaWriteDate;
    private LocalDateTime qnaUpdateDate;
    private int qnaHit;

    @OneToOne(mappedBy = "qna", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private QnAFile qnAFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private Member member;



    public void WriteFormSetQnA(QnAForm qnAForm) {
        this.qnaTitle = qnAForm.getQnaTitle();
        this.qnaTitleClassification = qnAForm.getQnaTitleClassification();
        this.qnaContent = qnAForm.getQnaContent();
        this.qnaHit = qnAForm.getQnaHit();
        this.qnaWriteDate = LocalDateTime.now();
        this.qnaUpdateDate = LocalDateTime.now();
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
        this.qnaUpdateDate = LocalDateTime.now();
    }



}
