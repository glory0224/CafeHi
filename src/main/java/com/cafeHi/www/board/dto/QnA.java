package com.cafeHi.www.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.cafeHi.www.member.dto.Member;
import com.cafeHi.www.member.dto.MemberAuth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class QnA {
	
	private int qna_num;	// QnA 게시글 번호 
	private String qna_title; // QnA 게시글 제목
	private String qna_title_classification; // 사용자 권한별 QnA 제목 분류 
	private String qna_content; // QnA 게시글 내용
	private LocalDateTime qna_writetime; // QnA 게시글 작성일
	private LocalDateTime qna_updatetime; // QnA 게시글 수정일
	private int qna_hit;	// QnA 게시글 조회수
	private String upload_path;	// QnA 게시글 업로드 경로 
	private String store_file_name; // QnA 게시글 서버 저장 파일명
	private String upload_file_name; // QnA 게시글 클라이언트 저장 파일명
	
	private Member member; // 멤버 정보를 가져오기 위한 필드

	
	// 권한 리스트
	private List<MemberAuth> authList;

	
	// QnA 등록 시  
	public void setQnADateTime() {
		this.qna_writetime = LocalDateTime.now();
		this.qna_updatetime = LocalDateTime.now();
	}
	
	// QnA 수정 시
	public void updateQnADateTime() {
		this.qna_updatetime = LocalDateTime.now();
	}

	// QnA 파일 저장
	public void saveFile(String storeFileName, String uploadFileName, String fullPath) {
		this.store_file_name = storeFileName;
		this.upload_file_name = uploadFileName;
		this.upload_path = fullPath;
	}


	public QnA(String qna_title, String qna_title_classification, String qna_content, LocalDateTime qna_writetime,
			LocalDateTime qna_updatetime, int qna_hit, String upload_path, String store_file_name,
			String upload_file_name, Member member) {
		super();
		this.qna_title = qna_title;
		this.qna_title_classification = qna_title_classification;
		this.qna_content = qna_content;
		this.qna_writetime = qna_writetime;
		this.qna_updatetime = qna_updatetime;
		this.qna_hit = qna_hit;
		this.upload_path = upload_path;
		this.store_file_name = store_file_name;
		this.upload_file_name = upload_file_name;
		this.member = member;
	}
	
	
}
