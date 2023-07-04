package com.cafeHi.www.order.dto;

import com.cafeHi.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {


    private OrderStatus orderStatus; // 주문상태 - 주문, 취소



}
