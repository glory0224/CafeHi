package com.cafeHi.www.cart.service;

import com.cafeHi.cart.dto.CartForm;
import com.cafeHi.cart.dto.ModifyCartForm;
import com.cafeHi.cart.entity.Cart;
import com.cafeHi.cart.repository.CartRepository;
import com.cafeHi.member.entity.Member;
import com.cafeHi.member.repository.MemberRepository;
import com.cafeHi.menu.entity.Menu;
import com.cafeHi.menu.repository.MenuRepository;
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

    private final MemberRepository memberRepository;

    private final MenuRepository menuRepository;

    @Transactional
    public void insertCart(Long MemberCode, Long MenuId, int cartAmount){


        Member member = memberRepository.findMember(MemberCode);
        Menu menu = menuRepository.findMenu(MenuId);

        Cart cart = new Cart();

        cart.insertCartInfo(menu, member, cartAmount);

        cartRepository.saveCart(cart);

    };


    @Transactional
    public List<CartForm> findCartList(Long MemberCode) {

        List<Cart> cartList = cartRepository.findCartList(MemberCode);

        Member member = memberRepository.findMember(MemberCode);

        List<CartForm> cartFormList = new ArrayList<>();

        for (Cart cart : cartList) {
            CartForm cartForm = new CartForm(cart);
            cartFormList.add(cartForm);
        }

        return cartFormList;


    }

    @Transactional
    public void deleteCart(Long cartCode) {

        cartRepository.deleteCart(cartCode);
    }

    @Transactional
    public void modifyCart(ModifyCartForm cartForm) {
        // merge로 변경하는 것이 아닌 변경감지(더디체킹)으로 값을 변경한다.
        // Transactional 때문에 가능함
        Cart cart = cartRepository.findCart(cartForm.getCart_code());

        cart.modifyCartInfo(cartForm.getCart_amount());
    }

    @Transactional
    public void deleteAllCart(Long memberCode) {
        cartRepository.deleteAllCart(memberCode);
    }


    public double CalculateCartPoint(int sumMoney, Long memberCode) {
        Member member = memberRepository.findMember(memberCode);

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
