package com.cafeHi.www.mapper.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.order.dto.OrderMenu;

@Mapper
public interface OrderMenuMapper {

	public void insertOrderMenu(OrderMenu orderMenu);

	public List<OrderMenu> findOrderMenuList(int member_code);

    void cancelOrderMenu(OrderMenu orderMenu);
}
