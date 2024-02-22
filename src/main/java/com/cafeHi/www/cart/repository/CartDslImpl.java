package com.cafeHi.www.cart.repository;

import com.cafeHi.www.cart.entity.Cart;
import com.cafeHi.www.cart.entity.QCart;
import com.cafeHi.www.common.page.SearchCriteria;
import com.cafeHi.www.member.entity.QMember;
import com.cafeHi.www.menu.MenuType;
import com.cafeHi.www.menu.entity.QMenu;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartDslImpl implements CartDsl {

    private final JPAQueryFactory queryFactory;
    private final QCart cart = QCart.cart;
    private final QMenu qMenu = QMenu.menu;
    private final QMember member = QMember.member;

    @Override
    public List<Cart> findCartList(int limit, int offset, SearchCriteria searchCriteria, Long memberCode) {
        return queryFactory
                .select(cart)
                .from(cart)
                .where(cart.member.id.eq(memberCode).and(searchCartDateFilter(searchCriteria, cart).and(searchCartTypeFilter(searchCriteria, qMenu))))
                .orderBy(cart.id.desc())
                .offset(offset - 1)
                .limit(limit)
                .fetch();
    }

    private BooleanExpression searchCartTypeFilter(SearchCriteria searchCriteria, QMenu menu) {
        // Check if the enum contains the input string
        boolean containsInputString = false;
        for (MenuType value : MenuType.values()) {
            if (value.name().equals(searchCriteria.getKeyword())) {
                containsInputString = true;
                break;
            }
        }

        return searchCriteria.getSearchType().equals("menuName") ? cart.menu.menuName.contains(searchCriteria.getKeyword()) :
                searchCriteria.getSearchType().equals("menuType") ?  Expressions.asBoolean(containsInputString):    /* todo : 이 부분이 잘 동작하는지 테스트 할 것 */
                                null;
    }


    private BooleanExpression searchCartDateFilter(SearchCriteria searchCriteria, QCart cart) {
        //goe, loe 사용
        BooleanExpression isGoeStartDate = cart.cartWriteDate.goe(LocalDateTime.of(searchCriteria.getStartDate(), LocalTime.MIN));
        BooleanExpression isLoeEndDate = cart.cartWriteDate.loe(LocalDateTime.of(searchCriteria.getEndDate(), LocalTime.MAX).withNano(0));
        return Expressions.allOf(isGoeStartDate, isLoeEndDate);
    }
}
