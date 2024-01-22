package com.cafeHi.www.qna.dto;

import com.cafeHi.www.comment.dto.CommentDTO;
import com.cafeHi.www.member.dto.MemberDTO;
import com.cafeHi.www.qna.entity.QnA;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class QnADTO {

    private Long qnaNum;
    private String qnaTitle;
    private String qnaTitleClassification;
    private String qnaContent;
    private String formattedWriteDateTime;
    private String formattedUpdateDateTime;
    private LocalDate qnaWriteDate;
    private LocalDate qnaUpdateDate;
    private int qnaHit;
    private QnAFileDTO qnAFileDTO;
    private MemberDTO memberDTO;
    private List<CommentDTO> commentDTOList;

    public QnADTO(QnA qnA) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.qnaNum = qnA.getQnaNum();
        this.qnaTitle = qnA.getQnaTitle();
        this.qnaTitleClassification = qnA.getQnaTitleClassification();
        this.qnaContent = qnA.getQnaContent();
        this.formattedWriteDateTime = qnA.getQnaWriteDateTime().format(formatter);
        this.formattedUpdateDateTime = qnA.getQnaUpdateDateTime().format(formatter);
        this.qnaWriteDate = qnA.getQnaWriteDateTime().toLocalDate();
        this.qnaUpdateDate = qnA.getQnaUpdateDateTime().toLocalDate();
        this.qnaHit = qnA.getQnaHit();
        this.qnAFileDTO = new QnAFileDTO(qnA.getQnAFile());
        this.memberDTO = new MemberDTO(qnA.getMember());
        this.commentDTOList = qnA.getComments().stream().map(comment -> comment.convertCommentDTO()).collect(Collectors.toList());
    }
}
