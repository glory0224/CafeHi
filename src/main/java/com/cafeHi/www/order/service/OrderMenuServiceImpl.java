package com.cafeHi.www.order.service;

import com.cafeHi.www.mapper.order.OrderMenuMapper;
import com.cafeHi.www.order.dto.OrderMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMenuServiceImpl implements OrderMenuService{

    private final OrderMenuMapper orderMenuMapper;

    @Override
    @Transactional
    public void insertOrderMenu(OrderMenu orderMenu) {
        orderMenuMapper.insertOrderMenu(orderMenu);
    }

    @Override
    @Transactional
    public void cancelOrderMenu(OrderMenu orderMenu) {
        orderMenuMapper.cancelOrderMenu(orderMenu);
    }

    @Override
    public List<OrderMenu> findOrderMenuList(int member_code) {
        return orderMenuMapper.findOrderMenuList(member_code);
    }


}
