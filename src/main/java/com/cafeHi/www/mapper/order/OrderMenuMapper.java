package com.cafeHi.www.mapper.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.order.dto.OrderMenu;

@Mapper
public interface OrderMenuMapper {

	void insertOrderMenu(OrderMenu orderMenu);

	List<OrderMenu> findOrderMenuList(Long member_code);

    void cancelOrderMenu(OrderMenu orderMenu);
}
