package com.cafeHi.www.qna.controller;

import com.cafeHi.www.common.file.FileStore;
import com.cafeHi.www.common.page.PageMaker;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.qna.dto.QnAForm;
import com.cafeHi.www.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("member/qna")
public class QnAController {

    private final QnAService qnAService;

    private final FileStore fileStore;

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


    @PostMapping("WriteQnA")
    public String WriteQnA(@Valid QnAForm qnAForm, BindingResult result, MultipartFile uploadFile, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            return "member/cafehi_qnaWrite";
        }

        qnAService.WriteQnA(qnAForm, uploadFile);

        return "redirect:/CafeHi-QnAList";
    }

    @GetMapping("/CafeHi-QnA")
    public String QnAView(HttpServletRequest request, HttpServletResponse response, Long qnaNum, SearchCriteria searchCriteria, Model model) {


        qnAService.increaseHit(request, response, qnaNum);

        QnAForm qnAForm = qnAService.findQnA(qnaNum);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("securityId", authentication.getName()); // 로그인 중인 사용자의 권한에 따라 보이는 항목을 다르게 하기위해 ID값 반환
        model.addAttribute("qna", qnAForm);
        model.addAttribute("qnaFile", qnAService.findQnAFile(qnAForm.getQnaNum()));
        model.addAttribute("scri", searchCriteria);

        return "common/cafehi_qnaContent";
    }

    @PostMapping("/CafeHi-UpdateQnA")
    public String UpdateQnA(@Valid QnAForm qna, BindingResult result, MultipartFile uploadFile, SearchCriteria searchCriteria, RedirectAttributes redirectAttributes) {


        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("qna", qna);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.qna", result);
            return "redirect:/CafeHi-UpdateReturnQnA";
        }

        qnAService.UpdateQnA(qna, uploadFile);

        String queryString = searchCriteria.getQueryString(qna.getQnaNum(), searchCriteria.getPage(), searchCriteria.getPerPageNum(), searchCriteria.getSearchType(), searchCriteria.getKeyword());
        return "redirect:/CafeHi-QnA" + queryString;
    }

    @PostMapping("/CafeHi-DeleteQnA")
    public String DeleteQnA(QnAForm qnAForm, SearchCriteria searchCriteria) throws IOException {
        qnAService.deleteQnA(qnAForm);

        String queryString  = searchCriteria.getQueryString(searchCriteria.getPage(), searchCriteria.getPerPageNum(), searchCriteria.getSearchType(), searchCriteria.getKeyword());

        return "redirect:/CafeHi-QnAList" + queryString;
    }

    @PostMapping("/qnaFileDownload")
    public ResponseEntity<Resource> fileDownload(@RequestParam("upload_path") String upload_path, @RequestParam("upload_file_name") String upload_file_name) throws IOException {

        ResponseEntity<Resource> downloadResponse = fileStore.download(upload_file_name);


        if (downloadResponse.getStatusCode().is2xxSuccessful()) {
            try {
                // 파일 이름을 URLEncoder를 사용하여 인코딩합니다.
                String encodedFileName = URLEncoder.encode(upload_file_name, StandardCharsets.UTF_8.toString())
                        .replaceAll("\\+", "%20");

                // 다운로드한 파일을 Resource 객체로 변환
                ByteArrayResource resource = new ByteArrayResource(downloadResponse.getBody().getInputStream().readAllBytes());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDisposition(ContentDisposition.builder("attachment").filename(encodedFileName).build());

                // ResponseEntity를 생성하여 반환합니다.
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(resource.contentLength())
                        .body(resource);
            } catch (IOException e) {
                // 예외 처리 로직 추가
                e.printStackTrace();
                // 예외 발생 시 에러 응답을 반환하거나 다른 적절한 처리를 수행합니다.
                return ResponseEntity.status(500).build();
            }
        } else {
            // 파일 다운로드가 실패한 경우에 대한 처리 로직 추가
            return ResponseEntity.status(downloadResponse.getStatusCode()).build();
        }

    }

}
