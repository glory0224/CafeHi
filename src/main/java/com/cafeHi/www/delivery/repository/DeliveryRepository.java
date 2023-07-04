package com.cafeHi.www.delivery.repository;

import com.cafeHi.delivery.entity.Delivery;
import com.cafeHi.delivery.entity.QDelivery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class DeliveryRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public void saveDelivery(Delivery delivery) {
        if(delivery != null) {
            em.persist(delivery);
        }
    }


}
