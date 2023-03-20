package com.cafeHi.www.mapper.cart;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.cart.dto.Cart;

@Mapper
public interface CartMapper {
	
	public List<Cart> getCartList(int member_code);
	
	public int insertCart(Cart cart);
	
	public void modifyCart(Cart cart);
	
	public void deleteCart(int cart_code);
	
	public void deleteAllCart(int member_code);
	
	
	
	public int sumMoney(int member_code);

	

	
	
	
}
