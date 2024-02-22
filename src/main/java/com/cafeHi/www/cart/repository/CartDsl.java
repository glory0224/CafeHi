package com.cafeHi.www.cart.repository;

import com.cafeHi.www.cart.entity.Cart;
import com.cafeHi.www.common.page.SearchCriteria;

import java.util.List;

public interface CartDsl {
    List<Cart> findCartList(int limit, int offset, SearchCriteria searchCriteria, Long memberCode);

}
