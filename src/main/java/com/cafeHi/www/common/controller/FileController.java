package com.cafeHi.www.common.controller;

import com.cafeHi.common.file.S3FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final S3FileStore fileStore;

    // aws s3 파일 다운 받기
    @PostMapping("/qnaFileDownload")
    public ResponseEntity<Resource> fileDownload(@RequestParam("upload_path") String upload_path, @RequestParam("upload_file_name") String upload_file_name) throws IOException {

        ResponseEntity<byte[]> downloadResponse = fileStore.download(upload_path, upload_file_name);


        String encodedFileName = URLEncoder.encode(upload_file_name, StandardCharsets.UTF_8.toString())
                .replaceAll("\\+", "%20");

        // 다운로드한 파일을 Resource 객체로 변환
        ByteArrayResource resource = new ByteArrayResource(downloadResponse.getBody());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(encodedFileName).build());


        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(downloadResponse.getBody().length)
                .body(resource);
    }

}
