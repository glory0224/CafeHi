package com.cafeHi.www.common.file;

import com.cafeHi.www.common.file.dto.UploadFile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStore {

    public ResponseEntity<byte[]> download(String upload_path, String upload_file_name) {
        return null;
    }

    public void deleteFile(String FileName) {

    }

    public UploadFile storeFile(MultipartFile multipartFile){
        return null;
    }

    public String getFullPath(String FileName) {
        return null;
    }
}
