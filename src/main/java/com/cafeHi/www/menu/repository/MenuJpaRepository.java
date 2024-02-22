package com.cafeHi.www.menu.repository;

import com.cafeHi.www.menu.MenuType;
import com.cafeHi.www.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuJpaRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT mn FROM Menu mn WHERE mn.menuType = : menuType")
    List<Menu> findByMenuType(MenuType menuType);
}
