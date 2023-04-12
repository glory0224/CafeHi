package com.cafeHi.www.menu.service;

import com.cafeHi.www.mapper.menu.MenuMapper;
import com.cafeHi.www.menu.dto.MenuDTO;
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
    public List<MenuDTO> getCoffeeList() {

        return menuMapper.getCoffeeList();
    }

    @Override
    public List<MenuDTO> getColdBrewList() {

        return menuMapper.getColdBrewList();
    }

    @Override
    public List<MenuDTO> getLatteList() {
        return menuMapper.getLatteList();
    }

    @Override
    public List<MenuDTO> getSmoothieList() {
        return menuMapper.getSmoothieList();
    }

    @Override
    public List<MenuDTO> getSideList() {
        return menuMapper.getSideList();
    }

    @Override
    public List<MenuDTO> getBeverageList() {
        return menuMapper.getBeverageList();
    }

    @Override
    public List<MenuDTO> getFruitJuiceList() {
        return menuMapper.getFruitJuiceList();
    }

    @Override
    public List<MenuDTO> getTeaList() {
        return menuMapper.getTeaList();
    }

    @Override
    public MenuDTO getMenu(Long menu_code) {

        return menuMapper.getMenu(menu_code);
    }

    @Override
    @Transactional
    public void DecreaseMenuStockQuantity(MenuDTO getMenuDTO, int total_order_count) {

        getMenuDTO.DecreaseMenuStockQuantity(total_order_count);

        menuMapper.changeMenuStockQuantity(getMenuDTO);
    }

    @Override
    @Transactional
    public void IncreaseMenuStockQuantity(MenuDTO getMenuDTO, int total_order_count) {

        getMenuDTO.IncreaseMenuStockQuantity(total_order_count);

        menuMapper.changeMenuStockQuantity(getMenuDTO);
    }

    @Override
    public int findMenuStockQuantity(Long menu_code) {

        return menuMapper.findMenuStockQuantity(menu_code);
    }
}
