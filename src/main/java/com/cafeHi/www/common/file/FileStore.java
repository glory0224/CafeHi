package com.cafeHi.www.common.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // spring boot bean 자동 등록
public class FileStore {
	
	@Value("D:/Spring/member_upload/")
	private String fileDir;
	
	// 전체 경로 반환 메서드
	public String getFullPath(String filename) {
		return fileDir + filename;
	}
	
	// 여러 파일 저장
	public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
		List<UploadFile> storeFileResult = new ArrayList<>();
		
		for (MultipartFile multipartFile : multipartFiles) {
			if (!multipartFile.isEmpty()) {
				storeFileResult.add(storFile(multipartFile));	// 파일 저장 메서드로 반환받은 UploadFile 객체 add
			}
		}
		
		return storeFileResult;
		
	}
	
	// 파일 저장
	public UploadFile storFile(MultipartFile multipartFile) throws IOException{ 
		
		if (multipartFile.isEmpty()) {
			return null;
		}
		
		// 클라이언트 오리지널 파일명
		String originalFilename = multipartFile.getOriginalFilename();
		// 저장할 서버 파일명
		String storeFileName = createStoreFileName(originalFilename); // 서버 저장용 파일 이름 생성 메서드 
		
		multipartFile.transferTo(new File(getFullPath(storeFileName)));	// 전체 경로 반환 메서드로 파일 생성 
		
		return new UploadFile(originalFilename, storeFileName);
	}

	private String createStoreFileName(String originalFilename) {
		String ext = extractExt(originalFilename);
		String uuid = UUID.randomUUID().toString();
		
		return uuid + "." + ext; // UUID.확장자 방식으로 저장 - db 에서 조금 더 구분하기 쉽게 하기 위해서 확장자까지 저장하고자 한다. 
	}
	
	
	// 파일 확장자명 추출 메서드 
	private String extractExt(String originalFilename) {
		int position = originalFilename.lastIndexOf("."); // 확장자명(ex : txt) 앞에 '.' 에 해당하는 위치를 숫자 형태로 반환
		
		return originalFilename.substring(position + 1); // '.' 의 번호에 해당하는 위치 + 1 해주고 substring 으로 해당 위치 번호 뒤의 문자열을 반환,  '.' 뒤의 확장자가 반환됨
	}
	
	
}
