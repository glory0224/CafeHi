package com.cafeHi.www.delivery.entity;

import com.cafeHi.www.delivery.DeliveryStatus;
import com.cafeHi.www.delivery.dto.DeliveryDTO;
import com.cafeHi.www.delivery.dto.DeliveryForm;
import com.cafeHi.www.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
//@Setter
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    private String deliveryRoadAddress;

    private String deliveryJibunAddress;

    private String deliveryDetailAddress;

    private Boolean includeDeliveryFee; // 배송비 포함 여부

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 주문 상태

    private LocalDateTime deliveryStartDate;

    private LocalDateTime deliveryArriveDate;

    public static Delivery createDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryInfo(deliveryDTO);
        return delivery;
    }

    private void setDeliveryInfo(DeliveryDTO deliveryDTO){
        if (deliveryDTO.getDeliveryJibunAddress() == "") {
            this.deliveryJibunAddress = "지번주소없음";
        } else {
            this.deliveryJibunAddress = deliveryDTO.getDeliveryJibunAddress();
        }
        if (deliveryDTO.getDeliveryRoadAddress() == "") {
            this.deliveryRoadAddress = "도로명주소없음";
        } else {
            this.deliveryRoadAddress = deliveryDTO.getDeliveryRoadAddress();
        }
        this.deliveryDetailAddress = deliveryDTO.getDeliveryDetailAddress();
        this.deliveryStatus = DeliveryStatus.배송중;
        this.includeDeliveryFee = deliveryDTO.getIncludeDeliveryFee();
        this.deliveryStartDate = LocalDateTime.now();
        this.deliveryArriveDate = LocalDateTime.now().plusDays(1); // 1일 뒤 배송이라고 가정
    }

}
