package com.cafeHi.www.order.entity;

import com.cafeHi.www.delivery.DeliveryStatus;
import com.cafeHi.www.delivery.entity.Delivery;
import com.cafeHi.www.member.entity.Member;
import com.cafeHi.www.order.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // db order command 와 중복을 피하기 위해
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    @CreationTimestamp // 자동으로 저장 시간 삽입
    private LocalDateTime orderWritetime;	// 주문 날짜
    @UpdateTimestamp // 자동으로 수정시간을 삽입
    private LocalDateTime orderUpdatetime; // 수정 날짜

    @OneToMany(mappedBy = "order") // cascade 로 주문이 삭제시 자동 삭제
    private List<OrderMenu> orderMenuList = new ArrayList<>(); // 주문 메뉴

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //== 연관관계 메서드==//
    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenuList.add(orderMenu);
        orderMenu.setOrder(this);
    }

    //== 생성 메서드 == //
    public static Order createOrder(Member member, Delivery delivery, OrderMenu... orderMenuList) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderMenu orderMenu : orderMenuList) {
            order.addOrderMenu(orderMenu);
        }
        order.setOrderStatus(OrderStatus.주문완료);
        order.setOrderWritetime(LocalDateTime.now());
        return order;
    }

    // == 비지니스 로직 ==//

    public void cancel() {

        if (delivery.getDeliveryStatus() == DeliveryStatus.배송완료) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setOrderStatus(OrderStatus.주문취소);
        this.setOrderUpdatetime(LocalDateTime.now());

        for (OrderMenu orderMenu : orderMenuList) {
            orderMenu.cancel();
        }

    }



}
