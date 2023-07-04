package com.cafeHi.www.member.controller;

import com.cafeHi.www.member.dto.*;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.entity.MemberAuth;
import com.cafeHi.www.member.entity.Membership;
import com.cafeHi.www.member.service.MemberService;
import com.cafeHi.www.member.service.MembershipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final MembershipService membershipService;

    private final JavaMailSender mailSender;

    @GetMapping("/members/signup")
    public String signupForm(Model model){
        log.info("signupForm GetMapping");
        model.addAttribute("memberForm", new MemberForm());
        return "common/signup";
    }

    @PostMapping("/members/signup")
    public String signup(@Valid MemberForm memberForm, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "common/signup";
        }

        Member member = new Member();
        member.signupMember(memberForm.getMemberId(), memberForm.getMemberPw(), memberForm.getMemberName(), memberForm.getMemberContact(), memberForm.getMemberEmail()
                , memberForm.getMemberRoadAddress(), memberForm.getMemberJibunAddress(), memberForm.getMemberDetailAddress());

        try {

        Member getMember = memberService.joinMember(member);

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.signupMemberAuth(getMember);

        memberService.joinMemberAuth(memberAuth);

        Membership membership = new Membership();

        membership.signupMembership(getMember);

        membershipService.joinMembership(membership);

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "common/signup";
        }

        return "redirect:/login";
    }

    @GetMapping("/CafeHi-MemberInfoUpdate")
    public String MemberInfoUpdateView(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomMember memberInfo = (CustomMember) principal;

        Member findMember = memberService.findMember(memberInfo.getMemberInfo().getMemberCode());

        MemberForm memberForm = new MemberForm();

        memberForm.setId(findMember.getId());
        memberForm.setMemberId(findMember.getMemberId());
        memberForm.setMemberName(findMember.getMemberName());
        memberForm.setMemberContact(findMember.getMemberContact());
        memberForm.setMemberEmail(findMember.getMemberEmail());
        memberForm.setMemberRoadAddress(findMember.getMemberRoadAddress());
        memberForm.setMemberJibunAddress(findMember.getMemberJibunAddress());
        memberForm.setMemberDetailAddress(findMember.getMemberDetailAddress());

        model.addAttribute("memberForm", memberForm);

        return "member/cafehi_memberUpdate";
    }

    /**
     * 회원 정보 수정
     */
    @PostMapping("/CafeHi-MemberInfoUpdate")
    public String memberInfoUpdate(@ModelAttribute("memberForm") MemberForm form, Model model) {

        memberService.modifyMember(form);

        return "redirect:/CafeHi-MemberInfoUpdate";
    }

    /**
     *  회원 비밀번호 변경
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

    @PostMapping("/deleteMember")
    public String deleteMember(LoginForm loginForm, HttpSession session, HttpServletRequest request) {

        String memberId = loginForm.getMemberId();
        String memberPw = loginForm.getMemberPw();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomMember memberInfo = (CustomMember) principal;

        Long memberCode = memberInfo.getMemberInfo().getMemberCode();

        boolean isPasswordMatched = memberService.isPasswordMatched(memberCode, memberPw);

        if(memberId.equals(memberInfo.getUsername()) && isPasswordMatched) {
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

    @GetMapping("/EmailAuth")
    @ResponseBody
    public String mailAuth(String email) {


        /* 인증번호(난수) 생성 */
        Random random = new Random();
        // 111111 ~ 999999 범위의 숫자를 얻기 위해서 nextInt(888888) + 111111
        int checkNum = random.nextInt(888888) + 111111;


        /* 이메일 보내기 */

        String setFrom = "CafeHi1004@naver.com";
        String toMail = email;
        String title = "** 카페 하이 아이디 찾기 인증 관련 이메일입니다 **";
        String content =
                "홈페이지를 방문해주서서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + checkNum + "입니다." +
                        "<br>" +
                        "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";


        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        String num = Integer.toString(checkNum);

        log.info("아아디 찾기 인증번호  : {} ", num);

        return num;

    }

    /**
     * 아이디 찾기 - 해당 메일의 아이디 반환
     */
    @PostMapping("/FindMemberId")
    public String FindMemberId(FindMemberIdForm findMemberIdForm, Model model) {

        String memberIdByEmail = memberService.findMemberByEmail(findMemberIdForm.getMemberEmail());
        // 해당 메일이 없는 경우
        if (memberIdByEmail == null) {
            model.addAttribute("msg", "해당 메일로 가입된 아이디는 없습니다.");
            model.addAttribute("url", "javascript:history.back()");
            return "common/alert";
        } else {
            model.addAttribute("msg", "아이디는 " + memberIdByEmail + " 입니다.");
            model.addAttribute("url", "login");
            return "common/alert";
        }

    }

    /**
     * 비밀번호 찾기 - 해당 아이디에 비밀번호 찾기
     */
    @PostMapping("/FindMemberPw")
    public String FindMemberPw(@RequestParam String MemberId, Model model) {

        // 해당 계정의 새로운 비밀번호 반환

        ChangeMemberPwByEmailForm changeMemberPwByEmailForm = memberService.changeMemberPw(MemberId);

        /* 이메일 보내기 */
        String setFrom = "CafeHi1004@naver.com";
        String toMail = changeMemberPwByEmailForm.getEmail();
        String title = "** 카페하이 비밀번호 임시발급 **";
        String content =
                "홈페이지를 방문해주서서 감사합니다." +
                        "<br><br>" +
                        "임시 발급된 비밀번호는 " + changeMemberPwByEmailForm.getNewPassword() + "입니다." +
                        "<br>" +
                        "해당 사이트의 로그인 창에서 새로 입력해주세요" +
                        "<br>" +
                        "로그인 후 반드시 비밀번호를 변경해주세요.";


        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        log.info("전송된 이메일 주소 : {} ", changeMemberPwByEmailForm.getEmail());
        log.info("발급된 임시 비밀번호  : {} ", changeMemberPwByEmailForm.getNewPassword());

        model.addAttribute("msg", "가입하신 이메일로 임시 비밀번호가 발급되었습니다.");
        model.addAttribute("url", "login");
        return "common/alert";
    }

}
