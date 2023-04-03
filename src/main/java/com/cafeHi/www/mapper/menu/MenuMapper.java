package com.cafeHi.www.mapper.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.menu.dto.Menu;

@Mapper
public interface MenuMapper {

	List<Menu> getCoffeeList();

	List<Menu> getColdBrewList();

	List<Menu> getLatteList();

	List<Menu> getSmoothieList();

	List<Menu> getSideList();

	List<Menu> getBeverageList();

	List<Menu> getFruitJuiceList();

	List<Menu> getTeaList();

	Menu getMenu(Long menu_code);

    void changeMenuStockQuantity(Menu getMenu);
}
