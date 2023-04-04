package com.cafeHi.www.board.dto;

import com.cafeHi.www.member.dto.MemberDTO;
import com.cafeHi.www.member.dto.MemberAuthDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class QnADTO {

	private int qna_num;	// QnAEntity 게시글 번호
	private String qna_title; // QnAEntity 게시글 제목
	private String qna_title_classification; // 사용자 권한별 QnAEntity 제목 분류
	private String qna_content; // QnAEntity 게시글 내용
	private LocalDateTime qna_writetime; // QnAEntity 게시글 작성일
	private LocalDateTime qna_updatetime; // QnAEntity 게시글 수정일
	private int qna_hit;	// QnAEntity 게시글 조회수
	private String upload_path;	// QnAEntity 게시글 업로드 경로
	private String store_file_name; // QnAEntity 게시글 서버 저장 파일명
	private String upload_file_name; // QnAEntity 게시글 클라이언트 저장 파일명

	private MemberDTO member; // 멤버 정보를 가져오기 위한 필드


	// 권한 리스트
	private List<MemberAuthDTO> authList;


	// QnAEntity 등록 시
	public void setQnADateTime() {
		this.qna_writetime = LocalDateTime.now();
		this.qna_updatetime = LocalDateTime.now();
	}

	// QnAEntity 수정 시
	public void updateQnADateTime() {
		this.qna_updatetime = LocalDateTime.now();
	}

	// QnAEntity 파일 저장
	public void saveFile(String storeFileName, String uploadFileName, String fullPath) {
		this.store_file_name = storeFileName;
		this.upload_file_name = uploadFileName;
		this.upload_path = fullPath;
	}


	public QnADTO(String qna_title, String qna_title_classification, String qna_content, LocalDateTime qna_writetime,
                  LocalDateTime qna_updatetime, int qna_hit, String upload_path, String store_file_name,
                  String upload_file_name, MemberDTO member) {
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
