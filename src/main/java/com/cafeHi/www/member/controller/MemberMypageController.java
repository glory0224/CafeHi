package com.cafeHi.www.member.controller;

import com.cafeHi.www.cart.dto.CartForm;
import com.cafeHi.www.cart.service.CartService;
import com.cafeHi.www.common.page.PageMaker;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.common.page.WithoutKeywordCriteria;
import com.cafeHi.www.member.dto.*;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.service.MemberService;
import com.cafeHi.www.order.dto.OrderMenuDTO;
import com.cafeHi.www.order.dto.OrderSearch;
import com.cafeHi.www.order.service.OrderService;
import com.cafeHi.www.qna.dto.QnADTO;
import com.cafeHi.www.qna.service.QnAService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 마이페이지 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("member/mypage")
public class MemberMypageController {

    private final MemberService memberService;
    private final CartService cartService;
    private final OrderService orderService;
    private final QnAService qnAService;

    /**
     * ROLE_USER 마이페이지
     * @return
     */
    @GetMapping(value = "CafeHi-MemberMyPage")
    public String MemberMyPageView() {
        return "member/cafehi_myPage";
    }

    /**
     * ROLE_USER 마이페이지 - 내 정보 페이지
     * @param model
     * @return
     */
    @GetMapping("CafeHi-MemberInfo")
    public String MemberInfoView(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberInfo memberInfo = (MemberInfo) principal;

        Member findMember = memberService.findMember(memberInfo.getMemberCode());

        MemberForm memberForm = new MemberForm();

        memberForm.setId(findMember.getId());
        memberForm.setMemberId(findMember.getMemberId());
        memberForm.setMemberName(findMember.getMemberName());
        memberForm.setMemberContact(findMember.getMemberContact());
        memberForm.setMemberEmail(findMember.getMemberEmail());
        memberForm.setMemberZipCode(findMember.getMemberZipCode());
        memberForm.setMemberAddress(findMember.getMemberAddress());
        memberForm.setMemberDetailAddress(findMember.getMemberDetailAddress());

        model.addAttribute("memberForm", memberForm);

        return "member/cafehi_memberInfo";
    }

    /**
     * ROLE_USER 마이페이지 - 내정보 수정 페이지
     * @param model
     * @return
     */
    @GetMapping("CafeHi-MemberInfoUpdate")
    public String MemberInfoUpdateView(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberInfo memberInfo = (MemberInfo) principal;
        Member findMember = memberService.findMember(memberInfo.getMemberCode());

        MemberForm memberForm = new MemberForm();

        memberForm.setId(findMember.getId());
        memberForm.setMemberId(findMember.getMemberId());
        memberForm.setMemberName(findMember.getMemberName());
        memberForm.setMemberContact(findMember.getMemberContact());
        memberForm.setMemberEmail(findMember.getMemberEmail());
        memberForm.setMemberZipCode(findMember.getMemberZipCode());
        memberForm.setMemberAddress(findMember.getMemberAddress());
        memberForm.setMemberDetailAddress(findMember.getMemberDetailAddress());

        model.addAttribute("memberForm", memberForm);

        return "member/cafehi_memberUpdate";
    }

    /**
     * ROLE_USER 마이페이지 - 비밀번호 변경 페이지
     * @return
     */
    @GetMapping("CafeHi-MemberChangePassword")
    public String MemberChangePasswordView() {
        return "member/cafehi_memberPwChange";
    }

    /**
     * ROLE_USER 마이페이지 - 회원 탈퇴 페이지
     * @return
     */
    @GetMapping("CafeHi-MemberInfoDelete")
    public String MemberInfoDeleteView() {
        return "member/cafehi_memberDelete";
    }

    /**
     * ROLE_USER 마이페이지 - 멤버쉽 페이지
     * @param model
     * @return
     */
    @GetMapping("CafeHi-MyMembership")
    public String MyMembershipView(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        CustomUser memberInfo = (CustomUser) principal;
        MemberInfo memberInfo = (MemberInfo) principal;
        model.addAttribute("MembershipGrade", memberInfo.getMembership().getMembershipGrade());
        model.addAttribute("MemebershipPoint", memberInfo.getMembership().getMembershipPoint());

        return "member/cafehi_myMembership";
    }

    /**
     * ROLE_USER 마이페이지 - 나의 QnA
     * @param searchCriteria
     * @param model
     * @return
     */
    @GetMapping("/CafeHi-MyQna")
    public String myPageMyQnaView(SearchCriteria searchCriteria, Model model) {

        MemberInfo memberInfo = (MemberInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberCode = memberInfo.getMemberCode();

        int offset = searchCriteria.getRowStart();
        int limit = searchCriteria.getPerPageNum();

        List<QnADTO> qnAList = qnAService.findQnAListByMemberCode(limit, offset, searchCriteria, memberCode);

        PageMaker pageMaker = new PageMaker();

        pageMaker.setCri(searchCriteria);
        pageMaker.setTotalCount(qnAService.getPagingCount(searchCriteria));

        // 총 페이지 수
        model.addAttribute("MyQnaList", qnAList);
        model.addAttribute("MyQnaListSize",qnAList.size());
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("scri", searchCriteria);

        return "member/cafehi_myPageQnA";
    }

    /**
     * ROLE_USER 마이페이지 - 장바구니 페이지
     * @param model
     * @return
     */
    @GetMapping("/CafeHi-MyPageCart")
    public String myPageCartView(@AuthenticationPrincipal MemberInfo memberInfo, Model model) {
            SearchCriteria searchCriteria = new SearchCriteria();
            List<CartForm> cartList = cartService.findCartList(searchCriteria, memberInfo.getMemberCode());

            int sumMoney = 0;

            for (CartForm cartForm : cartList) {
                // 장바구니에 담긴 메뉴와 개수를 계산한 비용을 총 비용 변수에 더해준다.
                sumMoney += cartForm.getCartAmount() * cartForm.getMenuDTO().getMenuPrice();
            }

            // 합계에 대한 포인트 계산
            double totalPoint = cartService.CalculateCartPoint(sumMoney, memberInfo.getMemberCode());

            model.addAttribute("sumMoney", sumMoney);
            model.addAttribute("totalPoint", totalPoint);
            model.addAttribute("cartList", cartList);
            model.addAttribute("memberCode", memberInfo.getMemberCode());


        return "member/cafehi_myPageCart";
    }

    /**
     * ROLE_USER 마이페이지 - 주문목록 페이지
     * @param orderSearch
     * @param withoutKeywordCriteria
     * @param model
     * @return
     */
    @GetMapping("/CafeHi-MyPageOrderMenuList")
    public String CafehiOrderListView(@ModelAttribute("orderSearch") OrderSearch orderSearch, WithoutKeywordCriteria withoutKeywordCriteria, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberInfo memberInfo = (MemberInfo) principal;

        int offset = withoutKeywordCriteria.getRowStart();
        int limit = withoutKeywordCriteria.getPerPageNum();

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(withoutKeywordCriteria);

        if(memberInfo != null) {

            List<OrderMenuDTO> orderList = orderService.findOrders(limit, offset, withoutKeywordCriteria);
            pageMaker.setTotalCount(orderService.getPagingCount(withoutKeywordCriteria));

            model.addAttribute("orderList", orderList);
            model.addAttribute("pageMaker", pageMaker);
            model.addAttribute("scri", withoutKeywordCriteria);

        }

        return "member/cafehi_myPageOrderList";
    }

    /**
     * ROLE_USER 마이페이지 - 내정보 수정
     * @param form
     * @param model
     * @return
     */
    @PostMapping("CafeHi-MemberInfoUpdate")
    public String memberInfoUpdate(@ModelAttribute("memberForm") MemberForm form, Model model) {

        memberService.modifyMember(form);

        return "redirect:/member/mypage/CafeHi-MemberInfoUpdate";
    }

    /**
     * ROLE_USER 마이페이지 - 비밀번호 변경
     * @param changeMemberPwForm
     * @param request
     * @return
     */
    @PostMapping("/CafeHi-MemberChangePassword")
    public String memberChangePassword(ChangeMemberPwForm changeMemberPwForm, HttpServletRequest request) {

        Boolean isChangePw = memberService.authenticationAndChangePw(changeMemberPwForm);

        // 올바르게 변경됨
        if (isChangePw) {

            request.setAttribute("msg", "비밀번호가 변경되었습니다.");
            request.setAttribute("url", "javascript:history.back()");

            return "common/alert";

        }else { // 비밀번호가 서로 맞지 않음

            request.setAttribute("msg", "비밀번호가 맞지 않습니다. 가입된 비밀번호를 다시 입력해주세요.");
            request.setAttribute("url", "javascript:history.back()");

            return "common/alert";
        }

    }

    /**
     * ROLE_USER 마이페이지 - 회원 탈퇴
     * @param loginForm
     * @param session
     * @param request
     * @return
     */
    @PostMapping("/deleteMember")
    public String deleteMember(LoginForm loginForm, HttpSession session, HttpServletRequest request) {

        String memberId = loginForm.getMemberId();
        String memberPw = loginForm.getMemberPw();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        CustomUser memberInfo = (CustomUser) principal;
        MemberInfo memberInfo = (MemberInfo) principal;

        Long memberCode = memberInfo.getMemberCode();

        boolean isPasswordMatched = memberService.isPasswordMatched(memberCode, memberPw);

        if(memberId.equals(memberInfo.getMemberId()) && isPasswordMatched) {
            memberService.deleteMember(memberCode);
            session.invalidate(); // 세션 정보 삭제
            SecurityContextHolder.getContext().setAuthentication(null); // 세션 권한 정보 null 초기화
        } else {

            request.setAttribute("msg", "아이디 또는 비밀번호가 맞지 않습니다.");
            request.setAttribute("url", "javascript:history.back()");

            return "common/alert";
        }

        return "redirect:/";
    }


}