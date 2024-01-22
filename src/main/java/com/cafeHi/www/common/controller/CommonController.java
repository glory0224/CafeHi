package com.cafeHi.www.common.controller;

import com.cafeHi.www.common.page.PageMaker;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.dto.MemberForm;
import com.cafeHi.www.qna.dto.QnADTO;
import com.cafeHi.www.qna.form.QnAForm;
import com.cafeHi.www.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommonController {

	private final QnAService qnAService;

	@GetMapping("/")
	public String home() {
		return "common/main";
	}
	
	@GetMapping("/CafeHi-Introduce")
	public String Introduce() {
		
		return "common/cafehi_introduce";
		
	}
	
	@GetMapping("/CafeHi-Membership")
	public String Membership() {
		return "common/cafehi_membership";
		
	}
	
	@GetMapping("/CafeHi-Event")
	public String Event() {
		return "common/cafehi_event";
		
	}
	
	@GetMapping("/CafeHi-Place")
	public String Place() {
		return "common/cafehi_place";
		
	}


	@GetMapping("/CafeHi-Menu")
	public String Menu() {
		
		return "common/cafehi_menu";
	}

	@GetMapping("/CafeHi-Signup")
	public String signupForm(Model model){
		model.addAttribute("memberForm", new MemberForm());
		return "common/signup";
	}

	@GetMapping("CafeHi-FindMemberId")
	public String FindMemberIdView() {
		return "common/cafehi_findMemberId";
	}

	@GetMapping("CafeHi-FindMemberPw")
	public String FindMemberPw() {
		return "common/cafehi_findMemberPw";
	}

	// QnA 게시판
	@GetMapping("/CafeHi-QnAList")
	public String QnAListView(SearchCriteria searchCriteria, Model model) {

		int offset = searchCriteria.getRowStart();
		int limit = searchCriteria.getPerPageNum();
		List<QnADTO> qnAList = qnAService.findQnAList(limit, offset, searchCriteria);	// stackOverFlow Error

		PageMaker pageMaker = new PageMaker();

		pageMaker.setCri(searchCriteria);
		pageMaker.setTotalCount(qnAService.getPagingCount(searchCriteria));

		// qna 게시판 날짜 초기화 여부
		if(searchCriteria.getSearchStartDate() == "" && searchCriteria.getSearchEndDate() == "") {
			LocalDate currentDate = LocalDate.now();
			String strCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			model.addAttribute("qnaStartDate", strCurrentDate);
			model.addAttribute("qnaEndDate", strCurrentDate);
		} else {
			model.addAttribute("qnaStartDate", searchCriteria.getSearchStartDate());
			model.addAttribute("qnaEndDate", searchCriteria.getSearchEndDate());
		}


		// 총 페이지 수
		model.addAttribute("qnaList", qnAList);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("scri", searchCriteria);

		return "common/cafehi_qnaList";
	}
	
}
