package com.cafeHi.www.menu.service;

import com.cafeHi.www.mapper.menu.MenuMapper;
import com.cafeHi.www.menu.dto.Menu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService{

    private final MenuMapper menuMapper;


    @Override
    public List<Menu> getCoffeeList() {

        return menuMapper.getCoffeeList();
    }

    @Override
    public List<Menu> getColdBrewList() {

        return menuMapper.getColdBrewList();
    }

    @Override
    public List<Menu> getLatteList() {
        return menuMapper.getLatteList();
    }

    @Override
    public List<Menu> getSmoothieList() {
        return menuMapper.getSmoothieList();
    }

    @Override
    public List<Menu> getSideList() {
        return menuMapper.getSideList();
    }

    @Override
    public List<Menu> getBeverageList() {
        return menuMapper.getBeverageList();
    }

    @Override
    public List<Menu> getFruitJuiceList() {
        return menuMapper.getFruitJuiceList();
    }

    @Override
    public List<Menu> getTeaList() {
        return menuMapper.getTeaList();
    }

    @Override
    public Menu getMenu(int menu_code) {
        Menu menu = menuMapper.getMenu(menu_code);

        log.info("menu = {}", menu);

        return menuMapper.getMenu(menu_code);
    }

    @Override
    @Transactional
    public void DecreaseMenuStockQuantity(Menu getMenu, int total_order_count) {

        getMenu.DecreaseMenuStockQuantity(total_order_count);

        menuMapper.changeMenuStockQuantity(getMenu);
    }

    @Override
    @Transactional
    public void IncreaseMenuStockQuantity(Menu getMenu, int total_order_count) {

        getMenu.IncreaseMenuStockQuantity(total_order_count);

        menuMapper.changeMenuStockQuantity(getMenu);
    }
}
