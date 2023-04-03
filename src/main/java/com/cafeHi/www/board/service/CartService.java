package com.cafeHi.www.board.service;

import com.cafeHi.www.cart.dto.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getCartList(Long member_code);

    Long insertCart(Cart cart);

    void modifyCart(Cart cart);

    void deleteCart(Long cart_code);

    void deleteAllCart(Long member_code);

    int sumMoney(Long member_code);

}
