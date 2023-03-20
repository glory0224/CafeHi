package com.cafeHi.www.cart.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Cart {
	
	private int cart_code; // 장바구니 코드 (기본키)
	private int member_code; // 사용자 코드 
	private String member_name; // 사용자 이름
	private int menu_code; // 메뉴 코드 
	private String menu_name; // 메뉴 이름
	private int menu_price; // 메뉴 가격
	private int money; //  (menu_price*cart_amount) money -> 메뉴 가격 * 수량 값
	private int cart_amount; // 장바구니 양
	private LocalDateTime cart_writetime; // 장바구니 등록일
	private LocalDateTime cart_updatetime; // 장바구니 수정일
	
}
