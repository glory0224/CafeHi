package com.cafeHi.www.member.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 아이디 찾기 폼
 */
@Getter
@Setter
public class FindMemberIdForm {
    private String memberEmail;

    private boolean email_check_boolean;
}
