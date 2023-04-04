package com.cafeHi.www.mapper.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.cart.dto.CartDTO;

@Mapper
public interface CartMapper {
	
	List<CartDTO> getCartList(Long member_code);
	
	Long insertCart(CartDTO cartDTO);
	
	void modifyCart(CartDTO cartDTO);
	
	void deleteCart(Long cart_code);
	
	void deleteAllCart(Long member_code);
	
	
	
	int sumMoney(Long member_code);

	

	
	
	
}
