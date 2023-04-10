package com.cafeHi.www.cart.service;

import com.cafeHi.www.cart.dto.CartDTO;
import com.cafeHi.www.mapper.cart.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    @Override
    public Map<String, Object> getCartInfo(Long memberCode) {
        Map<String, Object> myPageCartMap = new ConcurrentHashMap<>();
        List<CartDTO> getCartListDTO = cartMapper.getCartList(memberCode); // 장바구니 목록
        int sumMoney = cartMapper.sumMoney(memberCode);
        int fee = sumMoney >= 30000 ? 0 : 2500;
        myPageCartMap.put("sumMoney", sumMoney);
        myPageCartMap.put("fee", fee); //배송료
        myPageCartMap.put("sum", sumMoney+fee); // 전체 금액
        myPageCartMap.put("CartList", getCartListDTO); // 장바구니 목록
        myPageCartMap.put("CartListSize", getCartListDTO.size()); // 레코드 개수

        return myPageCartMap;
    }

}
