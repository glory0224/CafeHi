package com.cafeHi.www.comment.dto;

import com.cafeHi.www.common.date.CommonAudit;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.qna.dto.QnAForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentForm extends CommonAudit {
    @Column(name = "comment_id")
    private Long commentId;
    @NotEmpty(message = "댓글 내용을 입력해주세요.")
    private String comment;
    private String member_code;

//    public CommentForm(Long commentId, String comment, String member_code) {
//        super();
//        this.commentId = commentId;
//        this.comment = comment;
//        this.member_code = member_code;
//    }
}
