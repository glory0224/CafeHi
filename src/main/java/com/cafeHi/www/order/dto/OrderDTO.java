package com.cafeHi.www.order.dto;

import com.cafeHi.www.delivery.dto.DeliveryDTO;
import com.cafeHi.www.member.dto.MemberDTO;
import com.cafeHi.www.order.OrderStatus;
import com.cafeHi.www.order.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderDTO {

    private Long id;

    private MemberDTO memberDTO;

    private OrderStatus orderStatus;

    private LocalDateTime orderWritetime;

    private LocalDateTime orderUpdatetime;

    private DeliveryDTO deliveryDTO;

    public OrderDTO(Order order) {
        this.id = order.getId();
        MemberDTO mem = new MemberDTO(order.getMember());
        this.memberDTO = mem;
        this.orderStatus = order.getOrderStatus();
        this.orderWritetime = order.getOrderWritetime();
        this.orderUpdatetime = order.getOrderUpdatetime();

        DeliveryDTO del = new DeliveryDTO();
        del.setDeliveryStatus(order.getDelivery().getDeliveryStatus());
        del.setIncludeDeliveryFee(order.getDelivery().getIncludeDeliveryFee());
        del.setDeliveryRoadAddress(order.getDelivery().getDeliveryRoadAddress());
        del.setDeliveryJibunAddress(order.getDelivery().getDeliveryJibunAddress());
        del.setDeliveryDetailAddress(order.getDelivery().getDeliveryDetailAddress());
        del.setDeliveryStartDate(order.getDelivery().getDeliveryStartDate());
        del.setDeliveryArriveDate(order.getDelivery().getDeliveryArriveDate());

        this.deliveryDTO = del;
    }


}
