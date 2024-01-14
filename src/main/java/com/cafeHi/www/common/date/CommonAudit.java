package com.cafeHi.www.common.date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class CommonAudit {

    private LocalDate qnaWriteDate; // 등록일
    private LocalDate qnaUpdateDate; // 수정일
    private LocalDateTime qnaWriteDateTime; // 등록일 + 등록 시간
    private LocalDateTime qnaUpdateDateTime;    // 수정일 + 수정 시간

}
