package com.cafeHi.www.menu.service;

import com.cafeHi.www.menu.MenuType;
import com.cafeHi.www.menu.dto.MenuDTO;
import com.cafeHi.www.menu.entity.Menu;
import com.cafeHi.www.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public void saveMenu(Menu menu) {
        menuRepository.save(menu);
    }

    public MenuDTO findMenu(Long MenuId) {

        Menu menu = menuRepository.findMenu(MenuId);

        MenuDTO menuDTO = new MenuDTO(menu);

        return menuDTO;
    }

    public List<Menu> findAllMenu() {
        return menuRepository.findAll();
    }

    public List<Menu> findMenuByType(MenuType MenuType) {
        return menuRepository.findByType(MenuType);
    }
}
