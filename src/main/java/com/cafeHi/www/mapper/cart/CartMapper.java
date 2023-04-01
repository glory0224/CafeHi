package com.cafeHi.www.mapper.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.cart.dto.Cart;

@Mapper
public interface CartMapper {
	
	List<Cart> getCartList(int member_code);
	
	int insertCart(Cart cart);
	
	void modifyCart(Cart cart);
	
	void deleteCart(int cart_code);
	
	void deleteAllCart(int member_code);
	
	
	
	int sumMoney(int member_code);

	

	
	
	
}
