package com.cafeHi.www.member.controller;

import com.cafeHi.www.member.repository.MemberRepository;
import com.cafeHi.www.member.service.AdminService;
import com.cafeHi.www.member.service.MemberService;
import com.cafeHi.www.qna.dto.QnAForm;
import com.cafeHi.www.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

    private final QnAService qnAService;

    // 권한 관리 페이지
    @GetMapping("managermanagement/CafeHi-AuthManagement")
    public String AuthManagementPage(Model model) {

        return "admin/cafehi_adminPage";
    }

    // 매니저 관리 페이지
    @GetMapping("managermanagement/CafeHi-ManagerManagement")
    public String ManagerMangementPage(Model model) {
        return "admin/cafehi_managerManagePage";
    }

}
