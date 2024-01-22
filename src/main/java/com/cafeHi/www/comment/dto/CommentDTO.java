package com.cafeHi.www.comment.dto;

import com.cafeHi.www.comment.entity.Comment;
import com.cafeHi.www.member.dto.MemberDTO;
import com.cafeHi.www.qna.dto.QnADTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentDTO {
    private Long commentId;
    private String comment;
    private MemberDTO memberDTO;
//    private QnADTO qnADTO;
    private String commentWriteDateTime;
    private String commentUpdateDateTime;

    public CommentDTO(Comment comment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.commentId = comment.getId();
        this.comment = comment.getComment();
        this.memberDTO = new MemberDTO(comment.getMember());
//        this.qnADTO = new QnADTO(comment.getQna());
        this.commentWriteDateTime = comment.getCommentWriteDateTime().format(formatter);
        this.commentUpdateDateTime = comment.getCommentUpdateDateTime().format(formatter);
    }
}
