package com.cafeHi.www.common.file;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadFile {
	
	private String uploadFileName; // 클라이언트 파일명
	private String storeFileName; // 서버 파일명
	
	
	public UploadFile(String uploadFileName, String storeFileName) {
		super();
		this.uploadFileName = uploadFileName;
		this.storeFileName = storeFileName;
	}
	
	
	
}
