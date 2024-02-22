package com.cafeHi.www.cart.service;

import com.cafeHi.www.cart.dto.CartForm;
import com.cafeHi.www.cart.dto.ModifyCartForm;
import com.cafeHi.www.cart.entity.Cart;
import com.cafeHi.www.cart.repository.CartJpaRepository;
import com.cafeHi.www.cart.repository.CartRepository;
import com.cafeHi.www.common.exception.EntityNotFoundException;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.member.repository.MemberJpaRepository;
import com.cafeHi.www.menu.entity.Menu;
import com.cafeHi.www.menu.repository.MenuJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CartService {

    private final CartRepository cartRepository;

    private final CartJpaRepository cartJpaRepository;

    private final MemberJpaRepository memberJpaRepository;

    private final MenuJpaRepository menuJpaRepository;

    @Transactional
    public void insertCart(Long memberCode, Long MenuId, int cartAmount){


        Member member = memberJpaRepository.findById(memberCode).orElseThrow(() -> new EntityNotFoundException("Not Found Member Info"));

        Menu menu = menuJpaRepository.findById(MenuId).orElseThrow(() -> new EntityNotFoundException("Not Found Menu Info"));

        Cart cart = new Cart();

        cart.insertCartInfo(menu, member, cartAmount);
        cartJpaRepository.save(cart);

    };


    @Transactional
    public List<CartForm> findCartList(SearchCriteria searchCriteria, Long memberCode) {
        int offset = searchCriteria.getRowStart();
        int limit = searchCriteria.getPerPageNum();
        List<Cart> cartList = cartJpaRepository.findCartList(limit, offset, searchCriteria, memberCode);

        List<CartForm> cartFormList = new ArrayList<>();

        for (Cart cart : cartList) {
            CartForm cartForm = new CartForm(cart);
            cartFormList.add(cartForm);
        }

        return cartFormList;


    }

    @Transactional
    public void deleteCart(Long cartCode) {

        cartJpaRepository.deleteById(cartCode);
    }

    @Transactional
    public void modifyCart(ModifyCartForm cartForm) {
        // merge로 변경하는 것이 아닌 변경감지(더디체킹)으로 값을 변경한다.
        // Transactional 때문에 가능함
        Cart cart = cartJpaRepository.findById(cartForm.getCart_code()).orElseThrow(() -> new EntityNotFoundException("Not Found Cart Info"));
        cart.modifyCartInfo(cartForm.getCart_amount());
    }

    @Transactional
    public void deleteAllCart(Long memberCode) {
        cartRepository.deleteAllCart(memberCode);
    }


    public double CalculateCartPoint(int sumMoney, Long memberCode) {

        Member member = memberJpaRepository.findById(memberCode).orElseThrow(() -> new EntityNotFoundException("Not Found Member Info"));

        double totalPoint = 0;

        if(member.getMembership().getMembershipGrade().toString().equals("VIP")) {
            if (sumMoney >= 30000) {
                totalPoint = sumMoney * 0.15;
            } else if (0 <= sumMoney && sumMoney < 30000) {
                totalPoint = (sumMoney + 2500) * 0.15;
            }
        } else if (member.getMembership().getMembershipGrade().toString().equals("GOLD")) {
            if (sumMoney >= 30000) {
                totalPoint = sumMoney * 0.10;
            } else if (0 <= sumMoney && sumMoney < 30000) {
                totalPoint = (sumMoney + 2500) * 0.10;
            }
        } else if (member.getMembership().getMembershipGrade().toString().equals("SILVER")) {
            if (sumMoney >= 30000) {
                totalPoint = sumMoney * 0.05;
            } else if (0 <= sumMoney && sumMoney < 30000) {
                totalPoint = (sumMoney + 2500) * 0.05;
            }
        } else if (member.getMembership().getMembershipGrade().toString().equals("STANDARD")) {
            if (sumMoney >= 30000) {
                totalPoint = sumMoney * 0.03;
            } else if (0 <= sumMoney && sumMoney < 30000) {
                totalPoint = (sumMoney + 2500) * 0.03;
            }
        }

        return totalPoint;
    }
}
