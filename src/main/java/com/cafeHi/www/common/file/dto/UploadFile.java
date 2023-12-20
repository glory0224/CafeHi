package com.cafeHi.www.common.file.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFile {

    private String uploadFileName; // 클라이언트 파일명
    private String storeFileName; // 서버 파일명
    private String contentType;


    public UploadFile(String uploadFileName, String storeFileName, String contentType) {
        super();
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.contentType = contentType;
    }



}
