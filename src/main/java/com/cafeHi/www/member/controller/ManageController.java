package com.cafeHi.www.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ManageController {

    // 유저 관리 페이지
    @GetMapping("/CafeHi-UserManagement")
    public String AuthManagementPage(Model model) {
        return "manager/cafehi_userManagementPage";
    }

    // 제품 관리 페이지
    @GetMapping("/CafeHi-ProductManagement")
    public String ManagerMangementPage(Model model) {
        return "manager/cafehi_productManagementPage";
    }

    @GetMapping("/api/messages")
    @ResponseBody
    public String messages() {
        return "messages_ok";
    }

}


