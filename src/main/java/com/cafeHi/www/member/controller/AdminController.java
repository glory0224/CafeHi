package com.cafeHi.www.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    // 권한 관리 페이지
    @GetMapping("/CafeHi-AuthManagement")
    public String AuthManagementPage(Model model) {
        return "admin/cafehi_adminPage";
    }

    // 매니저 관리 페이지
    @GetMapping("/CafeHi-ManagerManagement")
    public String ManagerMangementPage(Model model) {
        return "admin/cafehi_managerManagePage";
    }
}
