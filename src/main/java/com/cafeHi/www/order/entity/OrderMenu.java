package com.cafeHi.www.order.entity;

import com.cafeHi.menu.entity.Menu;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 생성자로 만든것과 동일
@ToString
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;	// 메뉴 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;	// 주문 정보

    private String orderMenuCode; // 주문 코드

    private int TotalOrderPrice; // 총 가격
    private int TotalOrderCount;	// 주문 총 수량

    private int TotalOrderPricePoint; // 총 주문에 대한 포인트

    private LocalDateTime orderMenuWritetime; // 주문 메뉴 등록일
    private LocalDateTime orderMenuUpdatetime; // 주문 메뉴 수정일

    private boolean orderMenuStatus; // 주문 메뉴 상태 (주문, 취소)

//    protected OrderMenu() { // new 생성을 막고 생성메서드를 통해서만 생성 가능하도록 하여 유지보수성을 높인다.
//    }

    //==생성 메서드==//

    public static OrderMenu createOrderMenu(Menu menu, int totalOrderPrice, int totalOrderCount, int totalOrderPricePoint, String orderMenuCode) { // orderPrice는 menu 테이블 컬럼도 똑같이 있지만 할인 같은 기능을 추가하는 것을 고려해서 따로 가져간다.
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.setMenu(menu);
        orderMenu.setOrderMenuCode(orderMenuCode);
        orderMenu.setTotalOrderPrice(totalOrderPrice);
        orderMenu.setTotalOrderCount(totalOrderCount);
        orderMenu.setTotalOrderPricePoint(totalOrderPricePoint);
        orderMenu.setOrderMenuWritetime(LocalDateTime.now());
        orderMenu.setOrderMenuUpdatetime(LocalDateTime.now());

        menu.removeStock(totalOrderCount);

        return orderMenu;
    }


    //== 비지니스 로직 ==//
    public void cancel() {
        menu.addStock(TotalOrderCount);
    }



}
