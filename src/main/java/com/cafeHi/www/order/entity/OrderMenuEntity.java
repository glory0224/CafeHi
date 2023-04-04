package com.cafeHi.www.order.entity;
import com.cafeHi.www.menu.entity.MenuEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class OrderMenuEntity {

    @Id @GeneratedValue
    @Column(name = "order_menu_code")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_code")
    private MenuEntity menuEntity;	// 메뉴 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_code")
    private OrderEntity orderEntity;	// 주문 정보
    private int totalOrderPrice; // 총 가격
    private int totalOrderCount;	// 주문 총 수량
    private LocalDateTime orderMenuWritetime; // 주문 메뉴 등록일
    private LocalDateTime orderMenuUpdatetime; // 주문 메뉴 수정일
    private int totalOrderPricePoint; // 총 주문 가격 적립 포인트
    private boolean orderMenuStatus; // 주문 메뉴 상태 (주문, 취소)

}
