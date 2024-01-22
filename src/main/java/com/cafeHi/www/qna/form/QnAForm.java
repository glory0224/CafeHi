package com.cafeHi.www.qna.form;

import com.cafeHi.www.common.date.CommonAuditForm;
import com.cafeHi.www.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QnAForm extends CommonAuditForm {

    @Column(name = "qna_num")
    private Long qnaNum;
    @NotEmpty(message = "게시글 제목을 입력해주세요.")
    private String qnaTitle;
    private String  qnaTitleClassification;
    @NotEmpty(message = "게시글 내용을 입력해주세요.")
    private String qnaContent;
    private int qnaHit;

}
