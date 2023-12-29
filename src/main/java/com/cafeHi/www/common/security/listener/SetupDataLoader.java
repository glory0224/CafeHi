package com.cafeHi.www.common.security.listener;

import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.entity.MemberAuth;
import com.cafeHi.www.member.entity.MemberAuthHierachy;
import com.cafeHi.www.member.entity.Membership;
import com.cafeHi.www.member.repository.MemberAuthHierarchyRepository;
import com.cafeHi.www.member.service.MemberService;
import com.cafeHi.www.member.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    private final MemberService memberService;
    private final MembershipService membershipService;
    @Autowired
    private MemberAuthHierarchyRepository memberAuthHierarchyRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        // 이미 설정되어 있는 경우
        if(alreadySetup) {
            return;
        }

        // 시큐리티 리소스 세팅
        createUserIfNotFound();

        alreadySetup = true;
    }

    @Transactional
    public void createUserIfNotFound() {

            // 테스트 멤버 생성
            Member user = new Member();
            user.signupMember("user1234", "user1234", "user", "01012341234", "test@test.com"
                    , "테스트 도로명 주소", "테스트 지번 주소", "테스트 상세주소");

            Member manager = new Member();
            manager.signupMember("manager1234", "manager1234", "manager", "01012341234", "test@test.com"
                    , "테스트 도로명 주소", "테스트 지번 주소", "테스트 상세주소");

            Member admin = new Member();
            admin.signupMember("admin1234", "admin1234", "admin", "01012341234", "test@test.com"
                    , "테스트 도로명 주소", "테스트 지번 주소", "테스트 상세주소");

            // 테스트 멤버 권한 생성
            Member getUser = memberService.joinMember(user);
            Member getManager = memberService.joinMember(manager);
            Member getAdmin = memberService.joinMember(admin);

            MemberAuth userAuth = new MemberAuth();
            MemberAuth managerAuth = new MemberAuth();
            MemberAuth adminAuth = new MemberAuth();

            userAuth.signupUserAuth(getUser);
            managerAuth.signupManagerAuth(getManager);
            adminAuth.signupAdminAuth(getAdmin);

            memberService.joinMemberAuth(userAuth);
            memberService.joinMemberAuth(managerAuth);
            memberService.joinMemberAuth(adminAuth);

            // 테스트 유저 멤버쉽 생성
            Membership membership = new Membership();
            membership.signupMembership(getUser);

            membershipService.joinMembership(membership);

            // 권한별로 계층 구조 적용
            // 유저 < 매니저 < 관리자
            createAuthHierarchyIfNotFound(managerAuth, adminAuth);
            createAuthHierarchyIfNotFound(userAuth, managerAuth);

    }

    @Transactional
    public void createAuthHierarchyIfNotFound(MemberAuth childAuth, MemberAuth parentAuth) {

        MemberAuthHierachy authHierachy = memberAuthHierarchyRepository.findByChildName(parentAuth.getMemberAuth());

        // 해당 권한의 계층구조가 없는 경우 생성
        if(authHierachy == null) {
            authHierachy = MemberAuthHierachy.builder()
                    .childName(parentAuth.getMemberAuth())
                    .build();
        }

        // 엔티티 저장, 부모 계층 데이터 추출
        MemberAuthHierachy parentAuthHierarchy = memberAuthHierarchyRepository.save(authHierachy);

        authHierachy = memberAuthHierarchyRepository.findByChildName(childAuth.getMemberAuth());

        // 해당 권한의 계층구조가 없는 경우 생성
        if(authHierachy == null) {
            authHierachy = MemberAuthHierachy.builder()
                    .childName(childAuth.getMemberAuth())
                    .build();
        }

        MemberAuthHierachy childAuthHierarchy = memberAuthHierarchyRepository.save(authHierachy);
        childAuthHierarchy.setParentName(parentAuthHierarchy);
    }
}
