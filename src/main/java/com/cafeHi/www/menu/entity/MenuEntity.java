package com.cafeHi.www.menu.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MenuEntity {

    @Id @GeneratedValue
    @Column(name = "menu_code")
    private Long id;

    private int menuPrice; // 메뉴 가격
    private String menuType; // 메뉴 종류
    private String menuName;	// 메뉴 이름
    private String menuExplain; // 메뉴 설명
    private String menuImgPath; // 메뉴 이미지 경로
    private int menuStockQuantity;  // 메뉴 재고 수량
    private LocalDateTime menuWritetime; // 메뉴 등록일
    private LocalDateTime menuUpdatetime; // 메뉴 수정일
}
