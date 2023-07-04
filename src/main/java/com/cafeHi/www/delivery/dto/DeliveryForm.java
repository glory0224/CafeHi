package com.cafeHi.www.delivery.dto;

import com.cafeHi.www.delivery.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 주문 페이지에서 주소 정보 입력하고 넘어올 때 사용할 DTO
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeliveryForm {

    private String deliveryRoadAddress;

    @NotEmpty(message = "주소 찾기로 주소를 등록해주세요.")
    private String deliveryJibunAddress;

    @NotEmpty(message = "상세주소가 입력되지 않았습니다.")
    private String deliveryDetailAddress;

    private Boolean includeDeliveryFee; // 배송비 포함 여부

    private int deliveryFee;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus; // 주문 상태

    private LocalDateTime deliveryStartDate;

    private LocalDateTime deliveryArriveDate;
}
