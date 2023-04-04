package com.cafeHi.www.cart.entity;

import com.cafeHi.www.member.entity.MemberEntity;
import com.cafeHi.www.menu.entity.MenuEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CartEntity {
    @Id
    @GeneratedValue
    @Column(name = "cart_code")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_code")
    private MenuEntity menuEntity;

    private int money; //  (menu_price*cart_amount) money -> 메뉴 가격 * 수량 값
    private int cartAmount; // 장바구니 양
    private LocalDateTime cartWritetime; // 장바구니 등록일
    private LocalDateTime cartUpdatetime; // 장바구니 수정일


}
