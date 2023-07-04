package com.cafeHi.www.qna.controller;

import com.cafeHi.common.page.SearchCriteria;
import com.cafeHi.qna.dto.QnAForm;
import com.cafeHi.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QnAPageController {

    private final QnAService qnAService;

    @GetMapping("/CafeHi-QnAWrite")
    public String QnaWriteView(Model model) {
        model.addAttribute("qnAForm", new QnAForm());
        return "member/cafehi_qnaWrite";
    }

    @GetMapping("/CafeHi-UpdateQnA")
    public String QnAUpdateView(Long qnaNum, Model model, SearchCriteria searchCriteria) {

        model.addAttribute("qna",qnAService.findQnA(qnaNum));
        model.addAttribute("qnaFile", qnAService.findQnAFile(qnaNum));
        model.addAttribute("scri", searchCriteria);

        return "member/cafehi_qnaUpdate";
    }

    @GetMapping("/CafeHi-UpdateReturnQnA")
    public String QnAUpdateReturnView(Model model, @ModelAttribute("qna") QnAForm qna,
                                      @ModelAttribute("org.springframework.validation.BindingResult.qna") BindingResult result, SearchCriteria searchCriteria) {

        model.addAttribute("scri", searchCriteria);

        if (result.hasErrors()) {
            // 에러가 있을 경우 처리
            model.addAttribute("qna", qnAService.findQnA(qna.getQnaNum()));
            model.addAttribute("org.springframework.validation.BindingResult.qna", result);
            return "member/cafehi_qnaUpdate";
        } else {
            // 에러가 없을 경우 처리
            model.addAttribute("qna", qnAService.findQnA(qna.getQnaNum()));
            model.addAttribute("qnaFile", qnAService.findQnAFile(qna.getQnaNum()));
            return "member/cafehi_qnaUpdate";
        }
    }
}
