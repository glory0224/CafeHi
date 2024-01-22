package com.cafeHi.www.comment.form;

import com.cafeHi.www.common.date.CommonAuditForm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
//@NoArgsConstructor
public class CommentForm extends CommonAuditForm {
    @Column(name = "comment_id")
    private Long commentId;
    @NotEmpty(message = "댓글 내용을 입력해주세요.")
    private String comment;
    private String qnaNum;

}
