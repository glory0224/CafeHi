package com.cafeHi.www.member.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 임시로 변경된 비밀번호 이메일 전송시 사용되는 폼
 */
@Getter
@Setter
public class ChangeMemberPwByEmailForm {

    private String email; // 가입된 이메일(보낼 이메일)

    private String newPassword; // 새로운 비밀번호
}
