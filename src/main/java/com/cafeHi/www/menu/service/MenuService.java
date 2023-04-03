package com.cafeHi.www.menu.service;

import com.cafeHi.www.menu.dto.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getCoffeeList();

    List<Menu> getColdBrewList();

    List<Menu> getLatteList();

    List<Menu> getSmoothieList();

    List<Menu> getSideList();

    List<Menu> getBeverageList();

    List<Menu> getFruitJuiceList();

    List<Menu> getTeaList();

    Menu getMenu(Long menu_code);

    void DecreaseMenuStockQuantity(Menu getMenu, int total_order_count);

    void IncreaseMenuStockQuantity(Menu getMenu, int total_order_count);

}
