package com.cafeHi.www.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class OrderListDTO {

    private double membershipPoint;

    private int totalOrderPrice;

    private int deliveryFee;

    private boolean includeDeliveryFee;
}
