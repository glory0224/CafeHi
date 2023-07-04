package com.cafeHi.www.menu.dto;


import com.cafeHi.www.menu.MenuType;
import com.cafeHi.www.menu.entity.Menu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MenuDTO {

    private Long menuId;

    private int menuPrice; // 메뉴 가격

    private MenuType menuType; // 메뉴 종류
    private String menuName;	// 메뉴 이름
    private String menuExplain; // 메뉴 설명
    private String menuImgPath; // 메뉴 이미지 경로
    private int menuStockQuantity;  // 메뉴 재고 수량
    private LocalDateTime menuWritetime; // 메뉴 등록일
    private LocalDateTime menuUpdatetime; // 메뉴 수정일

    public MenuDTO() {
    }

    public MenuDTO(Menu menu) {
        this.menuId = menu.getMenuId();
        this.menuPrice = menu.getMenuPrice();
        this.menuType = menu.getMenuType();
        this.menuName = menu.getMenuName();
        this.menuExplain = menu.getMenuExplain();
        this.menuImgPath = menu.getMenuImgPath();
        this.menuStockQuantity = menu.getMenuStockQuantity();
        this.menuWritetime = menu.getMenuWritetime();
        this.menuUpdatetime = menu.getMenuUpdatetime();
    }
}
