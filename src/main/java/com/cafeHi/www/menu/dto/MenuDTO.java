package com.cafeHi.www.menu.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MenuDTO {
	
	private Long menu_code; // 메뉴 코드
	private int menu_price; // 메뉴 가격
	private String menu_type; // 메뉴 종류
	private String menu_name;	// 메뉴 이름
	private String menu_explain; // 메뉴 설명
	private String menu_img_path; // 메뉴 이미지 경로 
	private int menu_stock_quantity;  // 메뉴 재고 수량
	private LocalDateTime menu_writetime; // 메뉴 등록일
	private LocalDateTime menu_updatetime; // 메뉴 수정일
	
	// 메뉴 정보 반환용 setter
	public void setMenu_code(Long menu_code) {

		this.menu_code = menu_code;
	}

	public void setMenu_stock_quantity(int menu_stock_quantity) {
		this.menu_stock_quantity = menu_stock_quantity;
	}

	// 메뉴 재고 수량 감소

	public void DecreaseMenuStockQuantity(int orderCount) {
		this.menu_stock_quantity = menu_stock_quantity - orderCount;
	}

	// 메뉴 재고 수량 증가

	public void IncreaseMenuStockQuantity(int orderCount) {
		this.menu_stock_quantity = menu_stock_quantity + orderCount;
	}

}
