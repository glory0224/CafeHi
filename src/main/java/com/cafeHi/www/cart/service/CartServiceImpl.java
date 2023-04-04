package com.cafeHi.www.cart.service;

import com.cafeHi.www.cart.dto.CartDTO;
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
    public List<CartDTO> getCartList(Long member_code) {
        return cartMapper.getCartList(member_code);
    }

    @Override
    @Transactional
    public Long insertCart(CartDTO cartDTO) {
        return cartMapper.insertCart(cartDTO);
    }

    @Override
    @Transactional
    public void modifyCart(CartDTO cartDTO) {
        cartMapper.modifyCart(cartDTO);
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
