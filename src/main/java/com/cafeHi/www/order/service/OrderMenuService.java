package com.cafeHi.www.order.service;

import com.cafeHi.www.order.dto.OrderMenuDTO;

import java.util.List;

public interface OrderMenuService {

    void insertOrderMenu(OrderMenuDTO orderMenuDTO);

    List<OrderMenuDTO> findOrderMenuList(Long member_code);

    void cancelOrderMenu(OrderMenuDTO orderMenuDTO);
}
