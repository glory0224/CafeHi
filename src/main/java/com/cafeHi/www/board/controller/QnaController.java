package com.cafeHi.www.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafeHi.www.common.page.PageMaker;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.dto.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafeHi.www.board.dto.QnA;
import com.cafeHi.www.common.file.FileStore;
import com.cafeHi.www.common.file.UploadFile;
import com.cafeHi.www.mapper.board.QnaMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QnaController {
	
	private final QnaMapper qnaMapper;
	
	private final FileStore fileStore;
	
	@PostMapping("WriteQnA")
	public String WriteQnA(@RequestParam(value = "uploadfile", required = false) MultipartFile uploadfile, QnA qna, Member mem) throws IOException {
		
		// 등록 및 수정 날짜 초기화 메서드
		qna.setQnADateTime();

		qna.setMember(mem);

		
		if (!uploadfile.isEmpty()) {
		
		UploadFile attachFile = fileStore.storFile(uploadfile);
		
		// 서버용 파일명
		String storeFile = attachFile.getStoreFileName();
		
		qna.setStore_file_name(storeFile);
		
		// 클라이언트 파일명
		String uploadFile = attachFile.getUploadFileName();
		
		qna.setUpload_file_name(uploadFile);
		
		// 전체 경로
		String fullPath = fileStore.getFullPath(storeFile);
		
		qna.setUpload_path(fullPath);
		
		} else {
			
			// 파일을 등록 안하는 경우
			qna.setStore_file_name("none");
			qna.setUpload_file_name("없음");
			qna.setUpload_path("none");
		}
		
		log.info("qna.member.member_code = {}", qna.getMember().getMember_code());

		qnaMapper.writeQnA(qna);
		
		
		return "redirect:/CafeHi-QnAList";
	}
	
	
	@PostMapping("/CafeHi-UpdateQnA")
	public String AUpdateQnA(@RequestParam(value = "uploadfile", required = false) MultipartFile uploadfile, QnA qna, HttpServletRequest request) throws IllegalStateException, IOException {
		
		
		// 수정 - 애초에 uploadfile 이 안넘어오거나, 새로운 파일이 넘어온다.
		
		// 이전에 있던 게시글 파일 존재 여부 확인 후
		QnA getQnA = qnaMapper.getQnA(qna);
		
		// 이전에 게시글에 파일이 존재 했다면 파일을 삭제, 새로운 파일을 넣거나 none	
		if (getQnA.getUpload_path() != "none") {
			
			File file = new File(qna.getUpload_path());
			file.delete();
			
			if (!uploadfile.isEmpty()) {
				
			UploadFile attachFile = fileStore.storFile(uploadfile);
			
			// 서버용 파일명
			String storeFile = attachFile.getStoreFileName();
			
			qna.setStore_file_name(storeFile);
			
			// 클라이언트 파일명
			String uploadFile = attachFile.getUploadFileName();
			
			qna.setUpload_file_name(uploadFile);
			
			// 전체 경로
			String fullPath = fileStore.getFullPath(storeFile);
			
			qna.setUpload_path(fullPath);
			
			} else {
				
				// 파일을 등록 안하는 경우
				qna.setStore_file_name("none");
				qna.setUpload_file_name("없음");
				qna.setUpload_path("none");
			}
			
		}else {
			
			// 이전에 게시글에 파일이 존재하지 않았다면 그냥 새로운 파일을 넣거나 none 
			
			if (!uploadfile.isEmpty()) {
				
				UploadFile attachFile = fileStore.storFile(uploadfile);
				
				// 서버용 파일명
				String storeFile = attachFile.getStoreFileName();
				
				qna.setStore_file_name(storeFile);
				
				// 클라이언트 파일명
				String uploadFile = attachFile.getUploadFileName();
				
				qna.setUpload_file_name(uploadFile);
				
				// 전체 경로
				String fullPath = fileStore.getFullPath(storeFile);
				
				qna.setUpload_path(fullPath);
				
				} else {
					
					// 파일을 등록 안하는 경우
					qna.setStore_file_name("none");
					qna.setUpload_file_name("없음");
					qna.setUpload_path("none");
				}
		}
		
		// 날짜 수정
		qna.updateQnADateTime();
		
		qnaMapper.modifyQnA(qna);
		request.setAttribute("msg", "수정이 완료되었습니다.");
		request.setAttribute("url", "CafeHi-QnA?qna_num=" + qna.getQna_num());
		return "common/alert";
	
	
}
	
	
	
	
	@GetMapping("/CafeHi-DeleteQnA")
	public String DeleteQnA(QnA qna, HttpServletRequest request) {
		
		QnA getQnA = qnaMapper.getQnA(qna);
		
		int result = qnaMapper.deleteQnA(getQnA);
		
		if(result != 0) {
			
			if(getQnA.getUpload_path() != "none") {
			// 저장된 경로가 none이 아니라면 해당 경로의 파일도 삭제
			File file = new File(getQnA.getUpload_path());
			file.delete();
			}
			
			request.setAttribute("msg", "삭제가 완료되었습니다.");
			request.setAttribute("url", "CafeHi-QnAList");
			return "common/alert";
		}else {
			
			request.setAttribute("msg", "삭제에 실패했습니다. 잠시 후 다시 시도해주세요.");
			request.setAttribute("url", "CafeHi-QnAList");
			
			return "common/alert";
		}
		
	}
	
	
	@RequestMapping("/CafeHi-QnAList")
	public String QnaListView(SearchCriteria scri, Model model) {

		List<QnA> qnaList = qnaMapper.getQnAList(scri);

		for(QnA qna : qnaList) {
			log.info("qna = {}", qna);
			log.info("qna.member_id = {}", qna.getMember().getMember_id());
		}

		model.addAttribute("qnaList", qnaList);
		model.addAttribute("qnaListSize", qnaList.size());

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);

		log.info("QnASearchNum = {}", qnaMapper.getQnASearchNum(scri));

		pageMaker.setTotalCount(qnaMapper.getQnASearchNum(scri));

		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("scri", scri);

		return "common/cafehi_qnaList";
	}
	
	@GetMapping("CafeHi-QnA")
	public String QnAView(HttpServletRequest request, HttpServletResponse response, QnA qna, Model model) {
		
		// 쿠키 생성으로 방문 했던 게시글 새로고침 할 때 계속 조회수 증가하는 현상 방지
		 Cookie oldCookie = null;
		 Cookie[] cookies = request.getCookies();
		 
		 // postView 라고 이름 지어진 쿠키가 있으면 새로운 쿠키 값을 넣는다. 
		 if(cookies != null) {
			 for (Cookie cookie : cookies) {
				 if (cookie.getName().equals("postView")) {
					 oldCookie = cookie;
				 }
			 }
		 }
		 
		 if (oldCookie != null) {
			 if(!oldCookie.getValue().contains("[" + qna.getQna_num() +"]")) {
				 qnaMapper.modifyHit(qna);
				 oldCookie.setValue(oldCookie.getValue() + "[" + qna.getQna_num() + "]");
				 oldCookie.setPath("/");
				 oldCookie.setMaxAge(60*60*24); // 쿠키 설정 시간 하루
				 response.addCookie(oldCookie);
			 }
		 
		 }else {
			 	
			 	qnaMapper.modifyHit(qna);
			 	Cookie newCookie = new Cookie("postView", "[" + qna.getQna_num() + "]");
			 	newCookie.setPath("/");
			 	newCookie.setMaxAge(60 * 60 * 24);
			 	response.addCookie(newCookie);
		 }
		 
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 model.addAttribute("securityId", authentication.getName()); // 로그인 중인 사용자의 권한에 따라 보이는 항목을 다르게 하기위해 ID값 반환 
		 model.addAttribute("qna", qnaMapper.getQnA(qna));
		
		return "common/cafehi_qnaContent";
	}
	
	
	
}
