package com.cafeHi.www.member.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 마이페이지에서 비밀번호 변경 시 사용되는 폼
 */
@Getter
@Setter
public class ChangeMemberPwForm {

    private long memberCode;

    private String currentPassword; // 가입된 이메일(보낼 이메일)

    private String changedPassword; // 새로운 비밀번호
}
