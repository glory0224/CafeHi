package com.cafeHi.www.delivery.dto;

import com.cafeHi.www.delivery.DeliveryStatus;
import com.cafeHi.www.delivery.entity.Delivery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeliveryDTO {

    private String deliveryRoadAddress;

    private String deliveryJibunAddress;

    private String deliveryDetailAddress;

    private Boolean includeDeliveryFee; // 배송비 포함 여부

    private int deliveryFee;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 주문 상태

    private LocalDateTime deliveryStartDate;

    private LocalDateTime deliveryArriveDate;


    public DeliveryDTO(Delivery delivery) {
        this.deliveryRoadAddress = delivery.getDeliveryRoadAddress();
        this.deliveryJibunAddress = delivery.getDeliveryJibunAddress();
        this.deliveryDetailAddress = delivery.getDeliveryDetailAddress();
        this.deliveryStatus = delivery.getDeliveryStatus();
        this.deliveryStartDate = delivery.getDeliveryStartDate();
        this.deliveryArriveDate = delivery.getDeliveryArriveDate();
    }
}
