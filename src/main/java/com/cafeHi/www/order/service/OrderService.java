package com.cafeHi.www.order.service;

import com.cafeHi.www.order.dto.OrdersDTO;

public interface OrderService {
    Long insertOrder(OrdersDTO order);

    void cancelOrder(OrdersDTO order);

}
