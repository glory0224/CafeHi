package com.cafeHi.www.order.service;

import com.cafeHi.www.mapper.order.OrderMenuMapper;
import com.cafeHi.www.order.dto.OrderMenuDTO;
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
    public void insertOrderMenu(OrderMenuDTO orderMenuDTO) {
        orderMenuMapper.insertOrderMenu(orderMenuDTO);
    }

    @Override
    @Transactional
    public void cancelOrderMenu(OrderMenuDTO orderMenuDTO) {
        orderMenuMapper.cancelOrderMenu(orderMenuDTO);
    }

    @Override
    public List<OrderMenuDTO> findOrderMenuList(Long member_code) {
        return orderMenuMapper.findOrderMenuList(member_code);
    }


}
