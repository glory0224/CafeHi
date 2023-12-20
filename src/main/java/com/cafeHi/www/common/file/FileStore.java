package com.cafeHi.www.common.file;

import com.cafeHi.www.common.file.dto.UploadFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileStore {

    private final String uploadDir = "D:\\cafehi_file"; // 로컬 저장 경로

    public ResponseEntity<Resource> download(String uploadFileName) {

        try {
            Path filePath = Paths.get(uploadDir).resolve(uploadFileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Handle exception (e.g., log it)
            e.printStackTrace();
        }
    }

    public UploadFile storeFile(MultipartFile multipartFile) {
        try {
            // 파일 이름에서 잠재적인 보안 문제를 제거
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir);

            // 업로드 디렉토리가 존재하지 않으면 생성
            Files.createDirectories(uploadPath);

            // 저장할 파일 경로
            Path filePath = uploadPath.resolve(fileName).normalize();

            // 파일이 이미 존재하면 예외 발생
            if (Files.exists(filePath)) {
                throw new FileAlreadyExistsException("File already exists: " + fileName);
            }

            // 파일 복사
            Files.copy(multipartFile.getInputStream(), filePath);

            // 추가 정보를 저장할 경우, 예를 들어 데이터베이스에 파일 정보 저장
            UploadFile uploadFile = new UploadFile(fileName, filePath.toString(), multipartFile.getContentType());

            return uploadFile;

        } catch (IOException e) {
            // 파일 저장 중 에러 발생 시 예외 처리 (예를 들어, 로깅 등)
            e.printStackTrace();
            return null;
        }
    }

    // 파일의 전체 경로 얻기
    public String getFullPath(String fileName) {
        return Paths.get(uploadDir).resolve(fileName).normalize().toString();
    }
}
