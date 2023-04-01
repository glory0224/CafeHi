package com.cafeHi.www.board.service;

import com.cafeHi.www.cart.dto.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getCartList(int member_code);

    int insertCart(Cart cart);

    void modifyCart(Cart cart);

    void deleteCart(int cart_code);

    void deleteAllCart(int member_code);

    int sumMoney(int member_code);

}
