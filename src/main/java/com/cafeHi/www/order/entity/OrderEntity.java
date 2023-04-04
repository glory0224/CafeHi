package com.cafeHi.www.order.entity;

import com.cafeHi.www.member.entity.MemberEntity;
import com.cafeHi.www.order.OrderState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // db order command 와 중복을 피하기 위해
@Getter @Setter
public class OrderEntity {

    @Id @GeneratedValue
    @Column(name = "order_code")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code")
    private MemberEntity memberEntity;
    private OrderState orderStatus; // 주문 상태
    private LocalDateTime orderWritetime;	// 주문 날짜
    private LocalDateTime orderUpdatetime; // 수정 날짜
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private List<OrderMenuEntity> orderMenuEntityList = new ArrayList<>(); // 주문 메뉴
    private Boolean includeDelivery; // 배송비 포함 여부

    // 연관관계 메서드
    public void addOrderMenu(OrderMenuEntity orderMenuEntity) {
        orderMenuEntityList.add(orderMenuEntity);
        orderMenuEntity.setOrderEntity(this);
    }

}
