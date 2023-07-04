package com.cafeHi.www.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyCartForm {

    private Long cart_code;

    private int cart_amount;
}
