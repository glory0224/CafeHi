package com.cafeHi.www.menu.entity;

import com.cafeHi.www.common.exception.NotEnoughStockException;
import com.cafeHi.www.menu.MenuType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    private int menuPrice; // 메뉴 가격
    @Enumerated(EnumType.STRING)
    private MenuType menuType; // 메뉴 종류
    private String menuName;	// 메뉴 이름
    private String menuExplain; // 메뉴 설명
    private String menuImgPath; // 메뉴 이미지 경로
    private int menuStockQuantity;  // 메뉴 재고 수량
    private LocalDateTime menuWritetime; // 메뉴 등록일
    private LocalDateTime menuUpdatetime; // 메뉴 수정일

    // 응집도를 높이기 위해 비지니스 로직 추가
    // 재고 수량 증가
    public void addStock(int quantity) {
        this.menuStockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.menuStockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.menuStockQuantity = restStock;
    }


}
