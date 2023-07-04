package com.cafeHi.www.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class OrderMenuInfo {

    @NotNull(message = "수량을 선택해야 구매 가능합니다.")
    @Min(1)
    private int toOrderAmount;

    @NotNull(message = "메뉴가 선택되지 않았습니다.")
    private long menuId;

    private String returnPage;


}
