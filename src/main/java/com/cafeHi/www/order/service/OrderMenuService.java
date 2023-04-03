package com.cafeHi.www.order.service;

import com.cafeHi.www.order.dto.OrderMenu;

import java.util.List;

public interface OrderMenuService {

    void insertOrderMenu(OrderMenu orderMenu);

    List<OrderMenu> findOrderMenuList(Long member_code);

    void cancelOrderMenu(OrderMenu orderMenu);
}
