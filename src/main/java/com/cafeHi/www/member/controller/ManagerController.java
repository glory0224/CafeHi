package com.cafeHi.www.member.controller;

import com.cafeHi.www.common.page.PageMaker;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.service.ManagerService;
import com.cafeHi.www.menu.entity.Menu;
import com.cafeHi.www.qna.dto.QnAForm;
import com.cafeHi.www.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final QnAService qnAService;

    // 유저 관리 페이지
    @GetMapping("usermanagement/CafeHi-UserManagement")
    public String AuthManagementPage(Model model) {

        List<Member> membersByManager = managerService.findMembersByManager();

        model.addAttribute("userList", membersByManager);

        return "manager/cafehi_userManagementPage";
    }

    // 제품 관리 페이지
    @GetMapping("productmanagement/CafeHi-ProductManagement")
    public String ManagerMangementPage(Model model) {

        List<Menu> menuList = managerService.findMenuByManager();

        model.addAttribute("menuList", menuList);

        return "manager/cafehi_productManagementPage";
    }

    @GetMapping("/api/messages")
    @ResponseBody
    public String messages() {
        return "messages_ok";
    }

}


