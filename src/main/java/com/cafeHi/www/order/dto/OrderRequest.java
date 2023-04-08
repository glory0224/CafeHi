package com.cafeHi.www.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Long member_code;

    private Long menu_code;

    private int deliveryFee;
    private Boolean include_delivery;
    private int total_order_count;
    private int membership_point;
    private double membership_new_point;


}
