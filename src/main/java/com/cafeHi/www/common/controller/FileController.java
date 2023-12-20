package com.cafeHi.www.common.controller;

import com.cafeHi.www.common.file.FileStore;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileStore fileStore;

    // aws s3 파일 다운 받기
//    @PostMapping("/qnaFileDownload")
//    public ResponseEntity<Resource> fileDownload(@RequestParam("upload_path") String upload_path, @RequestParam("upload_file_name") String upload_file_name) throws IOException {
//
//        ResponseEntity<Resource> downloadResponse = fileStore.download(upload_file_name);
//
//
//        if (downloadResponse.getStatusCode().is2xxSuccessful()) {
//            try {
//                // 파일 이름을 URLEncoder를 사용하여 인코딩합니다.
//                String encodedFileName = URLEncoder.encode(upload_file_name, StandardCharsets.UTF_8.toString())
//                        .replaceAll("\\+", "%20");
//
//                // 다운로드한 파일을 Resource 객체로 변환
//                ByteArrayResource resource = new ByteArrayResource(downloadResponse.getBody().getInputStream().readAllBytes());
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                headers.setContentDisposition(ContentDisposition.builder("attachment").filename(encodedFileName).build());
//
//                // ResponseEntity를 생성하여 반환합니다.
//                return ResponseEntity.ok()
//                        .headers(headers)
//                        .contentLength(resource.contentLength())
//                        .body(resource);
//            } catch (IOException e) {
//                // 예외 처리 로직 추가
//                e.printStackTrace();
//                // 예외 발생 시 에러 응답을 반환하거나 다른 적절한 처리를 수행합니다.
//                return ResponseEntity.status(500).build();
//            }
//        } else {
//            // 파일 다운로드가 실패한 경우에 대한 처리 로직 추가
//            return ResponseEntity.status(downloadResponse.getStatusCode()).build();
//        }
//
//    }

}
