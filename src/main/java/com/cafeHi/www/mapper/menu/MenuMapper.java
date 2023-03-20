package com.cafeHi.www.mapper.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cafeHi.www.menu.dto.Menu;

@Mapper
public interface MenuMapper {

	public List<Menu> getCoffeeList();

	public List<Menu> getColdBrewList();

	public List<Menu> getLatteList();

	public List<Menu> getSmoothieList();

	public List<Menu> getSideList();

	public List<Menu> getBeverageList();

	public List<Menu> getFruitJuiceList();

	public List<Menu> getTeaList();

	public Menu getMenu(int menu_code);
}
