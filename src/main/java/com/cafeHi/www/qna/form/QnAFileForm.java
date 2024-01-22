package com.cafeHi.www.qna.form;

import com.cafeHi.www.qna.entity.QnA;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

@Getter
@Setter
@ToString
public class QnAFileForm {

    @Column(name = "file_code")
    private Long id;
    private String uploadPath; // 서버 파일 저장 경로
    private String storeFileName; // 서버 파일명
    private String uploadFileName; // 클라이언트 파일명

    @JoinColumn(name = "qna_num")
    private QnA qna;

}
