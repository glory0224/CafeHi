package com.cafeHi.www.cart.entity;

import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.menu.entity.Menu;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private Member member;

    private int cartAmount;

    private LocalDateTime cartWriteDate;

    private LocalDateTime cartUpdateDate;


    public void insertCartInfo(Menu menu, Member member, int cartAmount) {

        this.menu = menu;
        this.member = member;
        this.cartAmount = cartAmount;
        this.cartWriteDate = LocalDateTime.now();
        this.cartUpdateDate = LocalDateTime.now();
    }

    public void modifyCartInfo(int cartAmount) {
        this.cartAmount =cartAmount;
        this.cartUpdateDate = LocalDateTime.now();
    }

}
