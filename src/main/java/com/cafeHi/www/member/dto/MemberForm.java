package com.cafeHi.www.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class MemberForm {

    @Column(name = "member_code")
    private Long id;

    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "아이디를 입력해주세요. 아이디는 영문, 숫자 포함 6-20자로 입력해주세요.")
    private String memberId; // 사용자 아이디
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,19}$", message = "비밀번호를 입력해주세요. 비밀번호는 영문, 숫자 포함 6-20자로 입력해주세요.")
    private String memberPw; // 사용자 비밀번호
    @NotEmpty(message = "이름은 필수입니다.")
    private String memberName; // 사용자 이름
    @Pattern(regexp = "^01(0|1|6|7|8|9]{1})-?([0-9]{3,4})-?[0-9]{4}$", message = "연락처를 입력해주세요. ex) 010-1234-1234")
    private String memberContact; // 사용자 연락처
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일을 입력해주세요. ex) cafeHi@naver.com")
    private String memberEmail; // 사용자 계정 인증용 이메일
    private String memberZipCode; // 우편번호
    @NotEmpty(message = "주소 찾기를 통해 주소를 등록해주세요.")
    private String memberAddress; // 사용자 주소
    @NotEmpty(message = "상세주소는 필수입니다.")
    private String memberDetailAddress; // 사용자 상세 주소
    private boolean enabled; // 스프링 시큐리티 권한 사용 여부
    private LocalDateTime memberWritetime; // 사용자 등록일
    private LocalDateTime memberUpdatetime; // 사용자 수정일
}
