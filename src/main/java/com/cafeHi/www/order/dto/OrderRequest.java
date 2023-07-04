package com.cafeHi.www.order.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {

    private Long memberCode;

    private Long menuId;

    private int totalOrderCount;

    private int totalOrderPrice;


}
