package com.cafeHi.www.menu.service;

import com.cafeHi.www.common.exception.EntityNotFoundException;
import com.cafeHi.www.menu.MenuType;
import com.cafeHi.www.menu.dto.MenuDTO;
import com.cafeHi.www.menu.entity.Menu;
import com.cafeHi.www.menu.repository.MenuJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuJpaRepository menuJpaRepository;

    @Transactional
    public void saveMenu(Menu menu) {

        menuJpaRepository.save(menu);
    }

    public MenuDTO findMenu(Long MenuId) {

        Optional<Menu> findMenu = menuJpaRepository.findById(MenuId);
        Menu menu = findMenu.orElseThrow(() -> new EntityNotFoundException("Not Found Menu Info"));
        MenuDTO menuDTO = new MenuDTO(menu);

        return menuDTO;
    }

    public List<Menu> findAllMenu() {
        return menuJpaRepository.findAll();
    }

    public List<Menu> findMenuByType(MenuType menuType) {
        return menuJpaRepository.findByMenuType(menuType);
    }
}
