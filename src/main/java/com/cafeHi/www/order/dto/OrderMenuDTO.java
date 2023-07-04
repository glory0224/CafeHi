package com.cafeHi.www.order.dto;

import com.cafeHi.delivery.dto.DeliveryDTO;
import com.cafeHi.menu.dto.MenuDTO;
import com.cafeHi.order.entity.OrderMenu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderMenuDTO {

    private Long id;

    private MenuDTO menuDTO;

    private OrderDTO orderDTO;

    private DeliveryDTO deliveryDTO;

    private String orderMenuCode; // 주문 코드

    private int TotalOrderPrice; // 총 가격
    private int TotalOrderCount;	// 주문 총 수량

    private int TotalOrderPricePoint; // 총 주문에 대한 포인트

    private LocalDateTime orderMenuWritetime; // 주문 메뉴 등록일
    private LocalDateTime orderMenuUpdatetime; // 주문 메뉴 수정일

    private boolean orderMenuStatus; // 주문 메뉴 상태 (주문, 취소)

    public OrderMenuDTO(OrderMenu orderMenu, MenuDTO menuDTO, OrderDTO orderDTO, DeliveryDTO deliveryDTO) {
        this.id = orderMenu.getId();
        this.menuDTO = menuDTO;
        this.orderDTO = orderDTO;
        this.deliveryDTO = deliveryDTO;
        this.orderMenuCode = orderMenu.getOrderMenuCode();
        this.TotalOrderPrice = orderMenu.getTotalOrderPrice();
        this.TotalOrderCount = orderMenu.getTotalOrderCount();
        this.TotalOrderPricePoint = orderMenu.getTotalOrderPricePoint();
        this.orderMenuWritetime = orderMenu.getOrderMenuWritetime();
        this.orderMenuUpdatetime = orderMenu.getOrderMenuUpdatetime();
        this.orderMenuStatus = orderMenu.isOrderMenuStatus();
    }
}
