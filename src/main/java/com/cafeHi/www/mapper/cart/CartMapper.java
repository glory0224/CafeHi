package com.cafeHi.www.mapper.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.cart.dto.Cart;

@Mapper
public interface CartMapper {
	
	List<Cart> getCartList(Long member_code);
	
	Long insertCart(Cart cart);
	
	void modifyCart(Cart cart);
	
	void deleteCart(Long cart_code);
	
	void deleteAllCart(Long member_code);
	
	
	
	int sumMoney(Long member_code);

	

	
	
	
}
