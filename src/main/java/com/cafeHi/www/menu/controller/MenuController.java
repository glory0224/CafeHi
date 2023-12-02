package com.cafeHi.www.menu.controller;

import com.cafeHi.www.menu.MenuType;
import com.cafeHi.www.menu.entity.Menu;
import com.cafeHi.www.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final MenuRepository menuRepository;

    @GetMapping("/menuList/coffee")
    public String coffeeListView(Model model) {

        List<Menu> coffeeList = menuRepository.findByType(MenuType.coffee);

        model.addAttribute("coffeeList0", coffeeList.get(0));
        model.addAttribute("coffeeList1", coffeeList.get(1));
        model.addAttribute("coffeeList2", coffeeList.get(2));
        model.addAttribute("coffeeList3", coffeeList.get(3));
        model.addAttribute("coffeeList4", coffeeList.get(4));


        return "menu/cafehi_coffee";


    }


    @GetMapping("/menuList/coldBrew")
    public String coldBrewListView(Model model) {
        List<Menu> coldbrewList =  menuRepository.findByType(MenuType.coldbrew);

        model.addAttribute("coldbrewList0", coldbrewList.get(0));
        model.addAttribute("coldbrewList1", coldbrewList.get(1));
        model.addAttribute("coldbrewList2", coldbrewList.get(2));
        model.addAttribute("coldbrewList3", coldbrewList.get(3));
        model.addAttribute("coldbrewList4", coldbrewList.get(4));

        return "menu/cafehi_coldbrew";
    }

    @GetMapping("/menuList/fruitJuice")
    public String fruitJuiceListView(Model model) {
        List<Menu> fruitJuiceList =  menuRepository.findByType(MenuType.fruitJuice);

        model.addAttribute("fruitJuiceList0", fruitJuiceList.get(0));
        model.addAttribute("fruitJuiceList1", fruitJuiceList.get(1));
        model.addAttribute("fruitJuiceList2", fruitJuiceList.get(2));
        return "menu/cafehi_fruitsJuice";
    }

    @GetMapping("/menuList/beverage")
    public String beverageListView( Model model) {

        List<Menu> beverageList =  menuRepository.findByType(MenuType.beverage);

        model.addAttribute("beverageList0", beverageList.get(0));
        model.addAttribute("beverageList1", beverageList.get(1));
        model.addAttribute("beverageList2", beverageList.get(2));
        model.addAttribute("beverageList3", beverageList.get(3));
        model.addAttribute("beverageList4", beverageList.get(4));
        model.addAttribute("beverageList5", beverageList.get(5));
        model.addAttribute("beverageList6", beverageList.get(6));
        model.addAttribute("beverageList7", beverageList.get(7));
        model.addAttribute("beverageList8", beverageList.get(8));

        return "menu/cafehi_beverage";
    }

    @GetMapping("/menuList/latte")
    public String latteListView( Model model) {
        List<Menu> latteList =  menuRepository.findByType(MenuType.latte);

        model.addAttribute("latteList0", latteList.get(0));
        model.addAttribute("latteList1", latteList.get(1));
        model.addAttribute("latteList2", latteList.get(2));
        model.addAttribute("latteList3", latteList.get(3));
        model.addAttribute("latteList4", latteList.get(4));
        model.addAttribute("latteList5", latteList.get(5));
        model.addAttribute("latteList6", latteList.get(6));

        return "menu/cafehi_latte";
    }

    @GetMapping("/menuList/side")
    public String sideListView( Model model) {
        List<Menu> sideList =  menuRepository.findByType(MenuType.side);

        model.addAttribute("sideList0", sideList.get(0));
        model.addAttribute("sideList1", sideList.get(1));
        model.addAttribute("sideList2", sideList.get(2));
        model.addAttribute("sideList3", sideList.get(3));

        return "menu/cafehi_side&bruch";
    }

    @GetMapping("/menuList/smoothie")
    public String smoothieListView( Model model) {
        List<Menu> smoothieList =  menuRepository.findByType(MenuType.smoothie);

        model.addAttribute("smoothieList0", smoothieList.get(0));
        model.addAttribute("smoothieList1", smoothieList.get(1));
        model.addAttribute("smoothieList2", smoothieList.get(2));
        model.addAttribute("smoothieList3", smoothieList.get(3));
        model.addAttribute("smoothieList4", smoothieList.get(4));
        model.addAttribute("smoothieList5", smoothieList.get(5));

        return "menu/cafehi_smoothie";
    }

    @GetMapping("/menuList/tea")
    public String teaListView( Model model) {
        List<Menu> teaList =  menuRepository.findByType(MenuType.tea);

        model.addAttribute("teaList0", teaList.get(0));
        model.addAttribute("teaList1", teaList.get(1));
        model.addAttribute("teaList2", teaList.get(2));
        model.addAttribute("teaList3", teaList.get(3));
        model.addAttribute("teaList4", teaList.get(4));
        model.addAttribute("teaList5", teaList.get(5));
        model.addAttribute("teaList6", teaList.get(6));
        model.addAttribute("teaList7", teaList.get(7));
        model.addAttribute("teaList8", teaList.get(8));
        model.addAttribute("teaList9", teaList.get(9));
        model.addAttribute("teaList10", teaList.get(10));
        model.addAttribute("teaList11", teaList.get(11));
        model.addAttribute("teaList12", teaList.get(12));
        model.addAttribute("teaList13", teaList.get(13));
        model.addAttribute("teaList14", teaList.get(14));

        return "menu/cafehi_tea";
    }

}
