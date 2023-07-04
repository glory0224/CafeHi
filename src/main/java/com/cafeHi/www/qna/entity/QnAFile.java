package com.cafeHi.www.qna.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class QnAFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;
    private String uploadPath; // 서버 파일 저장 경로
    private String storeFileName; // 서버 파일명
    private String uploadFileName; // 클라이언트 파일명

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private QnA qna;


    public void AttachUploadFile(String uploadPath, String storeFileName, String uploadFileName) {
        this.uploadPath = uploadPath;
        this.storeFileName = storeFileName;
        this.uploadFileName = uploadFileName;
    }

    public void AttachNoneFile() {
        this.storeFileName = "none";
        this.uploadFileName = "없음";
        this.uploadPath = "none";
    }

    public void QnASetQnAFile(QnA findQnA) {
        this.qna = findQnA;
    }
}
