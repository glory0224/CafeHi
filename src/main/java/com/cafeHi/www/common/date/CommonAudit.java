package com.cafeHi.www.common.date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@AllArgsConstructor
public class CommonAudit {

    private LocalDate WriteDate; // 등록일
    private LocalDate UpdateDate; // 수정일
    private LocalDateTime WriteDateTime; // 등록일 + 등록 시간
    private LocalDateTime UpdateDateTime;    // 수정일 + 수정 시간

    public CommonAudit() {
        this.WriteDate = LocalDate.now();
        this.UpdateDate = LocalDate.now();
        this.WriteDateTime = LocalDate.now().atStartOfDay();
        this.UpdateDateTime = LocalDate.now().atStartOfDay();

    }
}
