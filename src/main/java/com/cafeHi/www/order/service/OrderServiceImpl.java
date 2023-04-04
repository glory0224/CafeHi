package com.cafeHi.www.order.service;

import com.cafeHi.www.mapper.order.OrderMapper;
import com.cafeHi.www.order.dto.OrdersDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderMapper orderMapper;

    @Override
    public Long insertOrder(OrdersDTO order) {
        return orderMapper.insertOrder(order);
    }

    @Override
    public void cancelOrder(OrdersDTO order) {
        orderMapper.cancelOrder(order);
    }
}
