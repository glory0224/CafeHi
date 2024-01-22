package com.cafeHi.www.qna.dto;

import com.cafeHi.www.qna.entity.QnAFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnAFileDTO {

    private Long fileId;
    private String uploadPath;
    private String storeFileName; // 서버 파일명
    private String uploadFileName; // 클라이언트 파일명

    public QnAFileDTO(QnAFile qnAFile) {
        this.fileId = qnAFile.getId();
        this.uploadPath = qnAFile.getUploadPath();
        this.storeFileName = qnAFile.getStoreFileName();
        this.uploadFileName = qnAFile.getUploadFileName();
    }
}
