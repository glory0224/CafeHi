package com.cafeHi.www.cart.repository;

import com.cafeHi.cart.entity.Cart;
import com.cafeHi.cart.entity.QCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public void saveCart(Cart cart) {
        em.persist(cart);
    }

    public Cart findCart(Long CartCode) {
        QCart cart = QCart.cart;
        return queryFactory
                .selectFrom(cart)
                .where(cart.id.eq(CartCode))
                .fetchOne();
    }


    public List<Cart> findCartList(Long memberCode) {
        QCart cart = QCart.cart;

        return queryFactory
                .select(cart)
                .from(cart)
                .where(cart.member.id.eq(memberCode))
                .fetch();
    }

    public void deleteCart(Long cartCode) {

        QCart cart = QCart.cart;

        queryFactory
                .delete(cart)
                .where(cart.id.eq(cartCode))
                .execute();
    }


    public void deleteAllCart(Long memberCode) {
        QCart cart = QCart.cart;

        queryFactory
                .delete(cart)
                .execute();
    }
}
