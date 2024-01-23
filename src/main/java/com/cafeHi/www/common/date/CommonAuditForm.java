package com.cafeHi.www.common.date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@ToString
public class CommonAuditForm {

    private String WriteDate; // 등록일
    private String UpdateDate; // 수정일
    private String WriteDateTime; // 등록일 + 등록 시간
    private String UpdateDateTime;    // 수정일 + 수정 시간

    public CommonAuditForm() {
        this.WriteDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.UpdateDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.WriteDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
        this.UpdateDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss"));
    }
}
