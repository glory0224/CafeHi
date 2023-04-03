package com.cafeHi.www.order.service;

import com.cafeHi.www.order.dto.Orders;

public interface OrderService {
    Long insertOrder(Orders order);

    void cancelOrder(Orders order);

}
