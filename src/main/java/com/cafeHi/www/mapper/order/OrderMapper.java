package com.cafeHi.www.mapper.order;


import com.cafeHi.www.order.dto.OrdersDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

	Long insertOrder(OrdersDTO order);

	void cancelOrder(OrdersDTO order);
	
}
