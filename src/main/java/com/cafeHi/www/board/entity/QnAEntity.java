package com.cafeHi.www.board.entity;

import com.cafeHi.www.member.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class QnAEntity {
    @Id @GeneratedValue
    @Column(name = "qna_num")
    private Long id;

    private String qnaTitle; // QnAEntity 게시글 제목
    private String qnaTitleClassification; // 사용자 권한별 QnAEntity 제목 분류
    private String qnaContent; // QnAEntity 게시글 내용
    private LocalDateTime qnaWritetime; // QnAEntity 게시글 작성일
    private LocalDateTime qnaUpdatetime; // QnAEntity 게시글 수정일
    private int qnaHit;	// QnAEntity 게시글 조회수
    private String uploadPath;	// QnAEntity 게시글 업로드 경로
    private String storeFileName; // QnAEntity 게시글 서버 저장 파일명
    private String uploadFileName; // QnAEntity 게시글 클라이언트 저장 파일명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private MemberEntity memberEntity; // 멤버 정보를 가져오기 위한 필드

}
