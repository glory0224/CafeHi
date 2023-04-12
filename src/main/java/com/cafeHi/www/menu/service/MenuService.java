package com.cafeHi.www.menu.service;

import com.cafeHi.www.menu.dto.MenuDTO;

import java.util.List;

public interface MenuService {

    List<MenuDTO> getCoffeeList();

    List<MenuDTO> getColdBrewList();

    List<MenuDTO> getLatteList();

    List<MenuDTO> getSmoothieList();

    List<MenuDTO> getSideList();

    List<MenuDTO> getBeverageList();

    List<MenuDTO> getFruitJuiceList();

    List<MenuDTO> getTeaList();

    MenuDTO getMenu(Long menu_code);

    void DecreaseMenuStockQuantity(MenuDTO getMenuDTO, int total_order_count);

    void IncreaseMenuStockQuantity(MenuDTO getMenuDTO, int total_order_count);

    int findMenuStockQuantity(Long menu_code);

}
