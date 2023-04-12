package com.cafeHi.www.mapper.menu;

import java.util.List;

import com.cafeHi.www.menu.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {

	List<MenuDTO> getCoffeeList();

	List<MenuDTO> getColdBrewList();

	List<MenuDTO> getLatteList();

	List<MenuDTO> getSmoothieList();

	List<MenuDTO> getSideList();

	List<MenuDTO> getBeverageList();

	List<MenuDTO> getFruitJuiceList();

	List<MenuDTO> getTeaList();

	MenuDTO getMenu(Long menu_code);

    void changeMenuStockQuantity(MenuDTO getMenuDTO);

	int findMenuStockQuantity(Long menu_code);
}
