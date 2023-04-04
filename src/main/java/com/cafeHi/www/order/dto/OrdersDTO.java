package com.cafeHi.www.order.dto;

import java.time.LocalDateTime;

import com.cafeHi.www.order.OrderState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

	private Long order_code;	// 기본키
	private Long member_code;	// 멤버 기본키
	private OrderState order_status; // 주문 상태
	private LocalDateTime order_writetime;	// 주문 날짜
	private LocalDateTime order_updatetime; // 수정 날짜 
	private OrderMenuDTO orderMenu; // 주문 메뉴
	private Boolean include_delivery; // 배송비 포함 여부
	
	
	
	
	
	// 주문 상태 , 주문 날짜 , 수정 날짜 등록 메서드 
	public void setTimeAndStatus() {
		this.order_status = OrderState.주문완료;
		this.order_writetime = LocalDateTime.now();
		this.order_updatetime = LocalDateTime.now();	
	}

	public void setOrderInfo(Long member_code, boolean include_delivery) {
		this.member_code = member_code;
		this.order_status = OrderState.주문완료;
		this.order_writetime = LocalDateTime.now();
		this.order_updatetime = LocalDateTime.now();
		this.include_delivery = include_delivery;

	}
	
	// 주문 상태, 수정 날짜 취소 메서드
	public void cancelTimeAndStatus() {
		this.order_status = OrderState.주문취소;
		this.order_updatetime = LocalDateTime.now();
	}
	
	// 
	public void setIncludeDelivery(Boolean IncludeDelivery) {
		this.include_delivery = IncludeDelivery;
	}


	
	// 주문 정보 전달용 setter

	public void setMember_code(Long member_code) {
		this.member_code = member_code;
	}

	public void setOrder_code(Long order_code) {

		this.order_code = order_code;
	}
}
