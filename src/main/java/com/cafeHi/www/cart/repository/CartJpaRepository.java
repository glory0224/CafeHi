package com.cafeHi.www.cart.repository;

import com.cafeHi.www.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartJpaRepository extends JpaRepository<Cart, Long>, CartDsl {

}
