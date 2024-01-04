package com.cafeHi.www.common.security.listener;

import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.entity.MemberAuth;
import com.cafeHi.www.member.entity.MemberAuthHierachy;
import com.cafeHi.www.member.entity.Membership;
import com.cafeHi.www.member.repository.MemberAuthHierarchyRepository;
import com.cafeHi.www.member.service.MemberService;
import com.cafeHi.www.member.service.MembershipService;
import com.cafeHi.www.resources.entity.Resources;
import com.cafeHi.www.resources.repository.ResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;
    private final MemberService memberService;
    private final MembershipService membershipService;
    @Autowired
    private MemberAuthHierarchyRepository memberAuthHierarchyRepository;

    @Autowired
    private ResourcesRepository resourcesRepository ;

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        // 이미 설정되어 있는 경우
        if(alreadySetup) {
            return;
        }

        // 시큐리티 리소스 세팅
        setupSecurityResources();

        alreadySetup = true;
    }

    @Transactional
    public void setupSecurityResources() {

            // 테스트 멤버 생성
        Member user = new Member();
        user.signupMember("user1234", "user1234", "user", "01012341234", "test@test.com"
                , "12345", "테스트 주소", "테스트 상세주소");

        Member manager = new Member();
        manager.signupMember("manager1234", "manager1234", "manager", "01012341234", "test@test.com"
                , "12345", "테스트 주소", "테스트 상세주소");

        Member admin = new Member();
        admin.signupMember("admin1234", "admin1234", "admin", "01012341234", "test@test.com"
                , "12345", "테스트 주소", "테스트 상세주소");

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

        // 권한별 리소스 설정
        Set<MemberAuth> userResourceSet = new HashSet<>();
        userResourceSet.add(userAuth);

        Set<MemberAuth> managerResourceSet = new HashSet<>();
        managerResourceSet.add(managerAuth);

        Set<MemberAuth> adminResourceSet = new HashSet<>();
        adminResourceSet.add(adminAuth);

        createResourceIfNotFound("/member/**", "", userResourceSet, "url");
        createResourceIfNotFound("/qna/member/**", "", userResourceSet, "url");
        createResourceIfNotFound("/manager/**", "", managerResourceSet, "url");
        createResourceIfNotFound("/qna/manager/**", "", managerResourceSet, "url");
        createResourceIfNotFound("/admin/**", "", adminResourceSet, "url");
        createResourceIfNotFound("/qna/admin/**", "", adminResourceSet, "url");

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

    @Transactional
    public Resources createResourceIfNotFound(String resourceName, String httpMethod, Set<MemberAuth> memberAuthSet, String resourceType) {
        Resources resources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);

        if (resources == null) {
            resources = Resources.builder()
                    .resourceName(resourceName)
                    .memberAuthSet(memberAuthSet)
                    .httpMethod(httpMethod)
                    .resourceType(resourceType)
                    .orderNum(count.incrementAndGet())  // 겹치는 리소스가 있는 경우 우선순위를 주기위한 orderNum 설정
                    .build();
        }

        return resourcesRepository.save(resources);
    }
}
