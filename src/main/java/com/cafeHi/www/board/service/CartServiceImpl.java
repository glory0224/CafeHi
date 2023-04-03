package com.cafeHi.www.board.service;

import com.cafeHi.www.cart.dto.Cart;
import com.cafeHi.www.mapper.cart.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartMapper cartMapper;

    @Override
    public List<Cart> getCartList(Long member_code) {
        return cartMapper.getCartList(member_code);
    }

    @Override
    @Transactional
    public Long insertCart(Cart cart) {
        return cartMapper.insertCart(cart);
    }

    @Override
    @Transactional
    public void modifyCart(Cart cart) {
        cartMapper.modifyCart(cart);
    }

    @Override
    @Transactional
    public void deleteCart(Long cart_code) {
        cartMapper.deleteCart(cart_code);
    }

    @Override
    @Transactional
    public void deleteAllCart(Long member_code) {
        cartMapper.deleteAllCart(member_code);
    }

    @Override
    public int sumMoney(Long member_code) {
        return cartMapper.sumMoney(member_code);
    }
}
