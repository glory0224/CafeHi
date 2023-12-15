package com.cafeHi.www.member.service;

import com.cafeHi.www.cart.entity.Cart;
import com.cafeHi.www.cart.repository.CartRepository;
import com.cafeHi.www.common.security.service.CustomUser;
import com.cafeHi.www.delivery.repository.DeliveryRepository;
import com.cafeHi.www.member.dto.ChangeMemberPwByEmailForm;
import com.cafeHi.www.member.dto.ChangeMemberPwForm;
import com.cafeHi.www.member.dto.MemberForm;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.entity.MemberAuth;
import com.cafeHi.www.member.repository.MemberRepository;
import com.cafeHi.www.order.entity.OrderMenu;
import com.cafeHi.www.order.repository.OrderRepository;
import com.cafeHi.www.qna.entity.QnA;
import com.cafeHi.www.qna.repository.QnAFileRepository;
import com.cafeHi.www.qna.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final QnARepository qnARepository;

    private final QnAFileRepository qnAFileRepository;

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;

    private final DeliveryRepository deliveryRepository;

    // 회원 가입
    @Transactional
    public Member joinMember(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증

        member.passwordEncode(member.getMemberPw()); // 패스워드 암호화

        memberRepository.saveMember(member);

        return member;
    }
    @Transactional
    public Long joinMemberAuth(MemberAuth memberAuth) {
        memberRepository.saveMemberAuth(memberAuth);
        return memberAuth.getId();
    }


    @Transactional
    public void modifyMember(MemberForm memberForm)  {
        // merge로 변경하는 것이 아닌 변경감지(더디체킹)으로 값을 변경한다.
        Member findMember = memberRepository.findMember(memberForm.getId());

        findMember.modifyMember(memberForm.getId(),memberForm.getMemberId(), memberForm.getMemberName(), memberForm.getMemberContact(), memberForm.getMemberEmail()
                , memberForm.getMemberRoadAddress(), memberForm.getMemberJibunAddress(), memberForm.getMemberDetailAddress());

        // 세션 정보 업데이트

        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 정보 업데이트
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        customUser.getMemberInfo().setMemberId(memberForm.getMemberId());
        customUser.getMemberInfo().setMemberName(memberForm.getMemberName());
        customUser.getMemberInfo().setMemberContact(memberForm.getMemberContact());
        customUser.getMemberInfo().setMemberEmail(memberForm.getMemberEmail());
        customUser.getMemberInfo().setMemberRoadAddress(memberForm.getMemberRoadAddress());
        customUser.getMemberInfo().setMemberJibunAddress(memberForm.getMemberJibunAddress());
        customUser.getMemberInfo().setMemberDetailAddress(memberForm.getMemberDetailAddress());

        // 인증 객체 업데이트
        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(customUser, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);


    }

    /**
     *
     * 회원 탈퇴 - 남아있는 게시글 확인, 삭제 후
     */
    @Transactional
    public void deleteMember(Long memberCode) {

        List<QnA> byMemberCodeQnAList = qnARepository.findQnAMemberCode(memberCode);
        List<OrderMenu> byMemberCodeOrderList = orderRepository.findOrderMenusByMemberCode(memberCode);
        List<Cart> byMemberCodeCartList = cartRepository.findCartList(memberCode);

        // 멤버가 게시글을 작성한 경우 게시글 삭제
        if (byMemberCodeQnAList.size() != 0) {
            for (QnA qna : byMemberCodeQnAList) {

                try {
                    if (!qna.getQnAFile().getUploadPath().equals("none")) {
                        File file = new File(qna.getQnAFile().getUploadPath());
                        if (file.exists()) {  // 파일이 존재하는 경우에만 삭제를 시도합니다.
                            if (!file.delete()) {  // 파일 삭제가 실패한 경우
                                throw new IOException("Failed to delete file: " + qna.getQnAFile().getUploadPath());
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to delete QnA file", e);
                }

                qnAFileRepository.deleteQnAFile(qna.getQnAFile().getId());
                qnARepository.deleteQnA(qna.getQnaNum());

            }

        }

        // 멤버가 장바구니를 담은 경우 장바구니 삭제
        if (byMemberCodeCartList.size() != 0) {
            cartRepository.deleteAllCart(memberCode);
        }

        // 멤버가 주문을 한 경우 주문 목록 삭제
        if (byMemberCodeOrderList.size() != 0) {
            for (OrderMenu orderMenu : byMemberCodeOrderList){

                orderRepository.deleteOrderMenu(orderMenu.getId());

                orderRepository.deleteOrder(orderMenu.getOrder().getId());

                orderRepository.deleteDelivery(orderMenu.getOrder().getDelivery().getId());

            }
        }

            memberRepository.deleteMember(memberCode);
        }


    private void validateDuplicateMember(Member member) {
        // 아이디가 중복 여부
        List<Member> findMembers = memberRepository.findById(member.getMemberId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("해당 아이디의 회원이 이미 존재합니다.");
        }

    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findMember(Long memberId) {
        return memberRepository.findMember(memberId);
    }

    public MemberAuth findMemberAuth(Long memberAuthId) {
        return memberRepository.findMemberAuth(memberAuthId);
    }


    public boolean isPasswordMatched(Long memberCode, String memberPw) {
        Member findMember = findMember(memberCode);
        String hashedPassword = findMember.getMemberPw();
        return new BCryptPasswordEncoder().matches(memberPw, hashedPassword);
    }


    public String findMemberByEmail(String memberEmail) {

        return memberRepository.findMemberByEmail(memberEmail);
    }

    // 아이디에 해당하는 계정의 비밀번호 변경
    @Transactional
    public ChangeMemberPwByEmailForm changeMemberPw(String memberId) {

        Member findMember = memberRepository.findByMemberId(memberId);

        // 랜덤으로 20자리 미만의 패스워드를 생성하는 비지니스 로직

        String newPassword = generateRandomPassword();

        // 변경 감지 방식으로 findMember의 비밀번호를 암호화 방식으로 저장
        findMember.passwordEncode(newPassword);

        // 변경된 비밀번호와 이메일 정보를 담아서 폼으로 반환
        ChangeMemberPwByEmailForm changeMemberPwByEmailForm = new ChangeMemberPwByEmailForm();

        changeMemberPwByEmailForm.setEmail(findMember.getMemberEmail());
        changeMemberPwByEmailForm.setNewPassword(newPassword); // 암호화 방식이 아닌 새로 발급한 비밀번호를 반환

        return changeMemberPwByEmailForm;
    }

    // 비밀번호 랜덤 생성
    private String generateRandomPassword() {
        int passwordLength = getRandomNumber(8, 20); // 8 ~ 20 랜덤 길이 추출
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = getRandomNumber(0, allowedCharacters.length() - 1);
            password.append(allowedCharacters.charAt(randomIndex));
        }

        return password.toString();
    }

    // 8 ~ 20 랜덤 수 뽑기
    private int getRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }


    // 비밀번호 변경 비지니스 로직
    @Transactional
    public Boolean authenticationAndChangePw(ChangeMemberPwForm changeMemberPwForm) {

        BCryptPasswordEncoder passwordEncode = new BCryptPasswordEncoder();

        // String encodePw = passwordEncode.encode(changeMemberPwForm.getCurrentPassword());

        Member member = memberRepository.findMember(changeMemberPwForm.getMemberCode());

        // 가져온 엔티티의 비밀번호와 인코딩한 비밀번호가 서로 일치한다면
        if (member != null && passwordEncode.matches(changeMemberPwForm.getCurrentPassword(), member.getMemberPw())) {
            member.passwordEncode(changeMemberPwForm.getChangedPassword()); // 비밀번호 변경
            return true;
        } else { // 아니면 false
            return false;
        }
    }
}
