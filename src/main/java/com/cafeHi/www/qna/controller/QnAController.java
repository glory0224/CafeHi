package com.cafeHi.www.qna.controller;

import com.cafeHi.www.comment.dto.CommentDTO;
import com.cafeHi.www.comment.form.CommentForm;
import com.cafeHi.www.comment.service.CommentService;
import com.cafeHi.www.common.file.FileStore;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.dto.MemberInfo;
import com.cafeHi.www.member.entity.MemberAuth;
import com.cafeHi.www.qna.dto.QnADTO;
import com.cafeHi.www.qna.form.QnAForm;
import com.cafeHi.www.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("qna")
public class QnAController {

    private final QnAService qnAService;

    private final FileStore fileStore;

    private final CommentService commentService;

    private String url;


    /**
     * QnA 글쓰기 페이지
     * @param model
     * @return
     */
    @GetMapping("/CafeHi-QnAWriteView")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String QnaWriteView(Model model, SearchCriteria searchCriteria) {

        model.addAttribute("qnAForm", new QnAForm());
        model.addAttribute("scri", searchCriteria);

        getAuthAndUrlMapping();
        return url;
    }

    /**
     * QnA 글쓰기
     * @param qnAForm
     * @param result
     * @param uploadFile
     * @return
     */
    @PostMapping("/WriteQnA")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String WriteQnA(@Valid QnAForm qnAForm, BindingResult result, MultipartFile uploadFile,Model model, SearchCriteria searchCriteria){

        if (result.hasErrors()) {
            model.addAttribute("scri", searchCriteria); // @Valid 로 검증하지 않는 검색조건에 관한 데이터 다시 반환
            getAuthAndUrlMapping();
            return url;
        }

        qnAService.WriteQnA(qnAForm, uploadFile);

        return "redirect:/CafeHi-QnAList";
    }

    /**
     * QnA 글수정 페이지
     * @param qnaNum
     * @param model
     * @param searchCriteria
     * @return
     */
    @GetMapping("/CafeHi-QnAUpdateView")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String QnAUpdateView(Long qnaNum, Model model, SearchCriteria searchCriteria) {

        model.addAttribute("qna",qnAService.findQnA(qnaNum));
        model.addAttribute("qnaFile", qnAService.findQnAFile(qnaNum));
        model.addAttribute("scri", searchCriteria);

        return "member/cafehi_qnaUpdate";
    }

    /**
     * QnA 글수정
     * @param qna
     * @param result
     * @param uploadFile
     * @param searchCriteria
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/UpdateQnA")
    public String UpdateQnA(@Valid QnAForm qna, BindingResult result, MultipartFile uploadFile, SearchCriteria searchCriteria, RedirectAttributes redirectAttributes) {


        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("qna", qna);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.qna", result);
            return "redirect:/qna/CafeHi-QnAUpdateView";
        }

        qnAService.UpdateQnA(qna, uploadFile);

        String queryString = searchCriteria.getQueryString(qna.getQnaNum(), searchCriteria.getPage(), searchCriteria.getPerPageNum(),
                searchCriteria.getSearchType(), searchCriteria.getKeyword(), searchCriteria.getSearchStartDate(), searchCriteria.getSearchEndDate());

        return "redirect:/qna/CafeHi-QnAUpdateView" + queryString;
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

    /**
     * QnA 글 조회
     * 글 조회는 모든 권한이 볼 수 있음
     * @param request
     * @param response
     * @param searchCriteria
     * @param model
     * @return
     */
    @GetMapping("/CafeHi-QnAView")
    public String QnAView(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("qna") QnADTO qna, @ModelAttribute("scri") SearchCriteria searchCriteria, Model model) {


        qnAService.increaseHit(request, response, qna.getQnaNum());

        QnADTO qnADTO = qnAService.findQnA(qna.getQnaNum());

        List<CommentDTO> commentDTOList = qnADTO.getCommentDTOList();

        for (CommentDTO commentDTO : commentDTOList) {
            System.out.println(commentDTO.toString());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        // 권한이 있는 사용자의 경우 securityId 추가
        if(principal instanceof MemberInfo) {
            MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
            model.addAttribute("securityId", memberInfo.getMemberId()); // 로그인 중인 사용자의 권한에 따라 보이는 항목을 다르게 하기위해 ID값 반환
        }
        model.addAttribute("qna", qnADTO);
        model.addAttribute("comments", commentDTOList);
        model.addAttribute("qnaFile", qnAService.findQnAFile(qnADTO.getQnaNum()));
        model.addAttribute("commentForm", new CommentForm());
        model.addAttribute("scri", searchCriteria);

        return "common/cafehi_qnaContent";
    }



    @PostMapping("CafeHi-DeleteQnA")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String DeleteQnA(QnAForm qnAForm, SearchCriteria searchCriteria, String securityId) throws IOException {

        qnAService.deleteQnA(qnAForm);

        String queryString  = searchCriteria.getQueryString(searchCriteria.getPage(), searchCriteria.getPerPageNum(), searchCriteria.getSearchType(),
                searchCriteria.getKeyword(), searchCriteria.getSearchStartDate(), searchCriteria.getSearchEndDate());

        return "redirect:/CafeHi-QnAList" + queryString;
    }

    @PostMapping("member/qnaFileDownload")
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

    @GetMapping("manager/CafeHi-QnA")
    public String QnAManagerView(HttpServletRequest request, HttpServletResponse response, Long qnaNum, SearchCriteria searchCriteria, Model model) {

        qnAService.increaseHit(request, response, qnaNum);

        QnADTO qnADTO = qnAService.findQnA(qnaNum);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("securityId", authentication.getName()); // 로그인 중인 사용자의 권한에 따라 보이는 항목을 다르게 하기위해 ID값 반환
        model.addAttribute("qna", qnADTO);
        model.addAttribute("qnaFile", qnAService.findQnAFile(qnADTO.getQnaNum()));
        model.addAttribute("scri", searchCriteria);

        return "common/cafehi_qnaContent";
    }


    /**
     * 세션에서 권한정보 가져와서 권한별 url 매핑하는 공통 메서드
     */
    private void getAuthAndUrlMapping() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        MemberAuth memberAuth = memberInfo.getMemberAuthEntities().get(0);

        if(memberAuth.getMemberAuth().equals("ROLE_USER")) {
            url = "member/cafehi_qnaWrite";
        } else if(memberAuth.getMemberAuth().equals("ROLE_MANAGER")) {
            url = "manager/cafehi_qnaWrite";
        } else if(memberAuth.getMemberAuth().equals("ROLE_ADMIN")) {
            url = "admin/cafehi_qnaWrite";
        }
    }
}
