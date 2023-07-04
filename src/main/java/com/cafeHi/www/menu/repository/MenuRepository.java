package com.cafeHi.www.menu.repository;

import com.cafeHi.menu.MenuType;
import com.cafeHi.menu.entity.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuRepository {

    private final EntityManager em;

    public void save(Menu menu) {
        if (menu.getMenuId() == null) {
            em.persist(menu);
        }
    }

    public Menu findMenu(Long id) {
        return em.find(Menu.class, id);
    }

    public List<Menu> findAll() {
        return em.createQuery("select mn from Menu mn", Menu.class)
                .getResultList();
    }

    // 메뉴 타입에 따른 메뉴 리스트 반환
    public List<Menu> findByType(MenuType menuType) {
        return em.createQuery("select mn from Menu mn where mn.menuType = : menuType", Menu.class)
                .setParameter("menuType", menuType)
                .getResultList();
    }

}
