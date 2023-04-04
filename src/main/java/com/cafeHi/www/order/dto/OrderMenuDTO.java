package com.cafeHi.www.order.dto;

import java.time.LocalDateTime;

import com.cafeHi.www.menu.dto.MenuDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMenuDTO {
	private int order_menu_code;	// 기본키 
	private MenuDTO menu;	// 메뉴 정보
	private OrdersDTO order;	// 주문 정보
	private int total_order_price; // 총 가격
	private int total_order_count;	// 주문 총 수량
	private LocalDateTime order_menu_writetime; // 주문 메뉴 등록일
	private LocalDateTime order_menu_updatetime; // 주문 메뉴 수정일
	private int total_order_price_point; // 총 주문 가격 적립 포인트
	private boolean order_menu_status; // 주문 메뉴 상태 (주문, 취소)
	

	
	// 배송비 포함 총 비용 계산
	public int  CalTotalPrice(int fee, int menu_price, int total_order_count) {
		int TotalPrice = (menu_price * total_order_count) + fee;
		
		return TotalPrice;
		
	}
	
	// 배송비 불포함 총 비용 계산
	public int CalTotalPrice(int menuPrice, int orderCount) {
			
		int TotalPrice = (menuPrice * orderCount);
			
		return TotalPrice;
	}
	
	public void setOrderMenuInfo(double total_order_price_point, int total_order_price , int total_order_count) {
		this.total_order_price_point = (int)total_order_price_point;
		this.order_menu_status = true;
		this.order_menu_writetime = LocalDateTime.now();
		this.order_menu_updatetime = LocalDateTime.now();
		this.total_order_price = total_order_price;
		this.total_order_count = total_order_count;

	}

	public void cancelTimeAndStatus() {
		this.order_menu_status = false;
		this.order_menu_updatetime = LocalDateTime.now();
	}

	public void setMenuDTO(MenuDTO menu) {
		this.menu = menu;
	}


	public void setOrder(OrdersDTO order) {
		this.order = order;
	}

	public void setOrder_menu_code(int order_menu_code) {
		this.order_menu_code = order_menu_code;
	}

	public void setTotal_order_price_point(int total_order_price_point) {
		this.total_order_price_point = total_order_price_point;
	}

	public void setTotal_order_count(int total_order_count) {
		this.total_order_count = total_order_count;
	}
}
