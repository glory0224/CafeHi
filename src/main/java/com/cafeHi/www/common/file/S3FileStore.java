package com.cafeHi.www.common.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.cafeHi.common.file.dto.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3FileStore {

    private final AmazonS3Client amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String bucketName;

    // 전체 경로 반환 메서드
    public String getFullPath(String filename) {
        return bucketName + filename;
    }

    // 여러 파일 저장
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));	// 파일 저장 메서드로 반환받은 UploadFile 객체 add
            }
        }

        return storeFileResult;
    }

    // 파일 저장
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile == null) {
            return null;
        }

        // 클라이언트 오리지널 파일명
        String originalFilename = multipartFile.getOriginalFilename();
        // 저장할 서버 파일명
        String storeFileName = createStoreFileName(originalFilename); // 서버 저장용 파일 이름 생성 메서드

        InputStream inputStream = multipartFile.getInputStream();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        String keyName = storeFileName; // S3 버킷 내에서의 파일 경로 및 이름

        // S3에 파일 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata));

        // S3에 업로드한 파일의 URL
        String uploadFileUrl = amazonS3Client.getUrl(bucketName, keyName).toString();

        return new UploadFile(originalFilename, uploadFileUrl);
    }

    // S3 파일 다운로드
    public ResponseEntity<byte[]> download(String fileUrl, String downloadedFileName) throws IOException {

        String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/uploads/") + "/uploads/".length());

        // S3 객체 다운로드
        S3Object s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName, fileKey));
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(s3ObjectInputStream);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(downloadedFileName).build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);

    }


    // S3 파일 삭제
    public void deleteFile(String storedFileName) {

        String fileKey = storedFileName.substring(storedFileName.lastIndexOf("/uploads/") + "/uploads/".length());

        amazonS3Client.deleteObject(bucketName, fileKey);
    }


    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext; // UUID.확장자 방식으로 저장 - DB에서 구분하기 쉽게 확장자까지 저장
    }

    // 파일 확장자명 추출 메서드
    private String extractExt(String originalFilename) {
        int position = originalFilename.lastIndexOf("."); // 확장자명(ex: txt) 앞에 '.' 에 해당하는 위치를 숫자 형태로 반환

        return originalFilename.substring(position + 1); // '.' 의 번호에 해당하는 위치 + 1 해주고 substring 으로 해당 위치 번호 뒤의 문자열을 반환,  '.' 뒤의 확장자가 반환됨
    }
}
