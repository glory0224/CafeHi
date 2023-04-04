package com.cafeHi.www.board.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cafeHi.www.board.dto.QnADTO;
import com.cafeHi.www.common.page.PageMaker;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cafeHi.www.mapper.board.QnaMapper;
import com.cafeHi.www.member.dto.CustomUser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QnaPageController {	
	
	
	private final QnaMapper qnaMapper;
	
	
	@GetMapping("/CafeHi-QnAWrite")
	public String QnaWriteView() {

		return "member/cafehi_qnaWrite";
	}
	
	@GetMapping("/CafeHi-UpdateQnA")
	public String QnAUpdateView(QnADTO qna, SearchCriteria scri, Model model) {
		
		model.addAttribute("qna", qnaMapper.getQnA(qna));
		model.addAttribute("modifyDate", LocalDateTime.now());
		model.addAttribute("scri", scri);
		return "member/cafehi_qnaUpdate";
	}
	
	
	// 회원이 쓴 QnAEntity List
	@RequestMapping("/CafeHi-MyPageQnA")
	public String MyPageQnAView(SearchCriteria scri, Model model) {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUser userInfo = (CustomUser) principal;
	    MemberDTO getMemberDTO = userInfo.getMemberDTO();
	  
	    Long member_code = getMemberDTO.getMember_code();

	    Map<String , Object> myPageQnAMap = new ConcurrentHashMap<>();
	    
	    myPageQnAMap.put("member_code", member_code);
		myPageQnAMap.put("scri", scri);
		List<QnADTO> MyQnaList = qnaMapper.getMyQnAList(myPageQnAMap);

		model.addAttribute("MyQnaList", MyQnaList);
		model.addAttribute("MyQnaListSize", MyQnaList.size());

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);

		pageMaker.setTotalCount(qnaMapper.getMyQnANum(myPageQnAMap));
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("scri", scri);


		return "member/cafehi_myPageQnA";
	}
	
	
	
}
