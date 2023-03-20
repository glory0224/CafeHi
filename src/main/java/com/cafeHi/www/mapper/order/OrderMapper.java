package com.cafeHi.www.mapper.order;


import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.order.dto.Orders;

@Mapper
public interface OrderMapper {

	public int insertOrder(Orders order);

	public void cancelOrder(Orders order);
	
}
