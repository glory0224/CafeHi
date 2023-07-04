package com.cafeHi.www.order.repository;

import com.cafeHi.common.page.WithoutKeywordCriteria;
import com.cafeHi.delivery.entity.Delivery;
import com.cafeHi.delivery.entity.QDelivery;
import com.cafeHi.member.entity.QMember;
import com.cafeHi.menu.entity.QMenu;
import com.cafeHi.order.OrderStatus;
import com.cafeHi.order.entity.Order;
import com.cafeHi.order.entity.OrderMenu;
import com.cafeHi.order.entity.QOrder;
import com.cafeHi.order.entity.QOrderMenu;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public void saveOrder(Order order) {
        if(order != null) {
            em.persist(order);
        }
    }

    public void saveOrderMenu(OrderMenu orderMenu) {
        if (orderMenu != null) {
            em.persist(orderMenu);
        }
    }


    public OrderMenu findOrder(Long OrderId) {
        return em.find(OrderMenu.class, OrderId);
    }

    public List<OrderMenu> findAll(int limit, int offset, WithoutKeywordCriteria withoutKeywordCriteria) {

        QOrder order = QOrder.order;
        QOrderMenu orderMenu = QOrderMenu.orderMenu;
        QDelivery delivery = QDelivery.delivery;
        QMenu menu = QMenu.menu;

        BooleanExpression orderStatusExpression = null;

        if (withoutKeywordCriteria.getSearchType().equals("주문완료")) {
            orderStatusExpression = order.orderStatus.eq(OrderStatus.주문완료);
        } else if (withoutKeywordCriteria.getSearchType().equals("주문취소")) {
            orderStatusExpression = order.orderStatus.eq(OrderStatus.주문취소);
        }

        return queryFactory
                .selectDistinct(orderMenu)
                .from(orderMenu)
                .join(orderMenu.order, order).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .join(orderMenu.menu, menu).fetchJoin()
                .where(orderStatusExpression)
                .orderBy(orderMenu.order.orderWritetime.desc())
                .offset(offset -1)
                .limit(limit)
                .fetch();
    }


    public int getPagingCount(WithoutKeywordCriteria withoutKeywordCriteria) {

        QOrder order = QOrder.order;
        QOrderMenu orderMenu = QOrderMenu.orderMenu;
        QDelivery delivery = QDelivery.delivery;
        QMenu menu = QMenu.menu;

        BooleanExpression orderStatusExpression = null;

        if (withoutKeywordCriteria.getSearchType().equals("주문완료")) {
            orderStatusExpression = order.orderStatus.eq(OrderStatus.주문완료);
        } else if (withoutKeywordCriteria.getSearchType().equals("주문취소")) {
            orderStatusExpression = order.orderStatus.eq(OrderStatus.주문취소);
        }

        long totalCount =  queryFactory
                .selectDistinct(orderMenu)
                .from(orderMenu)
                .join(orderMenu.order, order).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .join(orderMenu.menu, menu).fetchJoin()
                .where(orderStatusExpression)
                .fetchCount();

        return (int) totalCount;
    }

    public List<OrderMenu> findOrderMenusByMemberCode(Long memberCode) {
        QMember member = QMember.member;
        QOrder order = QOrder.order;
        QOrderMenu orderMenu = QOrderMenu.orderMenu;
        QDelivery delivery = QDelivery.delivery;

        return queryFactory
                .select(orderMenu)
                .from(order)
                .join(order.member, member)
                .join(order.orderMenuList, orderMenu)
                .join(order.delivery, delivery)
                .where(member.id.eq(memberCode))
                .fetch();
    }

    public void deleteOrderMenu(Long orderMenuId) {
        OrderMenu orderMenu = em.find(OrderMenu.class, orderMenuId);
        if (orderMenu != null) {
            em.remove(orderMenu);
        }
    }

    public void deleteOrder(Long orderId) {
        Order order = em.find(Order.class, orderId);

        if (order != null) {
            em.remove(order);
        }
    }

    public void deleteDelivery(Long deliveryId) {
        Delivery delivery = em.find(Delivery.class, deliveryId);
        if (delivery != null) {
            em.remove(delivery);
        }
    }


    public OrderMenu findOrderByOrderCode(String orderCode) {

        QOrderMenu orderMenu = QOrderMenu.orderMenu;

        return queryFactory
                .select(orderMenu)
                .from(orderMenu)
                .where(orderMenu.orderMenuCode.eq(orderCode))
                .fetchOne();

    }

    public List<OrderMenu> findOrderListByOrderCode(String orderCode) {
        QOrderMenu orderMenu = QOrderMenu.orderMenu;

        return queryFactory
                .select(orderMenu)
                .from(orderMenu)
                .where(orderMenu.orderMenuCode.eq(orderCode))
                .fetch();
    }

    public boolean isSingleOrder(String orderCode) {

        QOrderMenu orderMenu = QOrderMenu.orderMenu;

        Long count = queryFactory
                .select(orderMenu)
                .from(orderMenu)
                .where(orderMenu.orderMenuCode.eq(orderCode))
                .fetchCount();

        return count == 1;
    }
}
