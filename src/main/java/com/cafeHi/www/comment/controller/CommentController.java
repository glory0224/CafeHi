package com.cafeHi.www.comment.controller;

import com.cafeHi.www.comment.form.CommentForm;
import com.cafeHi.www.comment.service.CommentService;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.qna.dto.QnADTO;
import com.cafeHi.www.qna.form.QnAForm;
import com.cafeHi.www.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;

    private final QnAService qnAService;

    @PostMapping("/WriteComment")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String WriteComment(@Valid CommentForm commentForm, BindingResult result, Model model, Long qnaNum, SearchCriteria searchCriteria, RedirectAttributes redirectAttributes) {

        QnADTO qnA = qnAService.findQnA(qnaNum);

        if (result.hasErrors()) {
            model.addAttribute("qnaFile", qnAService.findQnAFile(qnaNum));
            model.addAttribute("qna", qnA);
            model.addAttribute("comments", qnA.getCommentDTOList());
            model.addAttribute("scri", searchCriteria);
            return "common/cafehi_qnaContent";
        }

        commentService.WriteComment(commentForm);
//        model.addAttribute("qnaFile", qnAService.findQnAFile(qnaNum));
        model.addAttribute("qna", qnA);
        model.addAttribute("scri", searchCriteria);

        redirectAttributes.addFlashAttribute("qna", qnA);
        redirectAttributes.addFlashAttribute("scri", searchCriteria);

        return "redirect:/qna/CafeHi-QnAView";
    }
}
