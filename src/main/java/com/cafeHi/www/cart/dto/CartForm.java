package com.cafeHi.www.cart.dto;

import com.cafeHi.cart.entity.Cart;
import com.cafeHi.menu.dto.MenuDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CartForm {

    private Long cart_code;

    private MenuDTO menuDTO;

    private int cartAmount;

    private LocalDateTime cartWriteDate;

    private LocalDateTime cartUpdateDate;

    public CartForm() {
    }

    public CartForm(Cart cart) {
        this.cart_code = cart.getId();
        this.menuDTO = new MenuDTO(cart.getMenu());
        this.cartAmount = cart.getCartAmount();
        this.cartWriteDate = cart.getCartWriteDate();
        this.cartUpdateDate = cart.getCartUpdateDate();
    }

}
