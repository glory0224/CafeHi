package com.cafeHi.www.order.service;

import com.cafeHi.www.order.dto.Orders;

public interface OrderService {
    int insertOrder(Orders order);

    void cancelOrder(Orders order);

}
