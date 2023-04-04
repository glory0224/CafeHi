package com.cafeHi.www.mapper.order;

import java.util.List;

import com.cafeHi.www.order.dto.OrderMenuDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMenuMapper {

	void insertOrderMenu(OrderMenuDTO orderMenuDTO);

	List<OrderMenuDTO> findOrderMenuList(Long member_code);

    void cancelOrderMenu(OrderMenuDTO orderMenuDTO);
}
