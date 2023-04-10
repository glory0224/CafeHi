package com.cafeHi.www.cart.service;

import com.cafeHi.www.cart.dto.CartDTO;

import java.util.List;
import java.util.Map;

public interface CartService {

    List<CartDTO> getCartList(Long member_code);

    Long insertCart(CartDTO cartDTO);

    void modifyCart(CartDTO cartDTO);

    void deleteCart(Long cart_code);

    void deleteAllCart(Long member_code);

    int sumMoney(Long member_code);

    Map<String, Object> getCartInfo(Long memberCode);

}
