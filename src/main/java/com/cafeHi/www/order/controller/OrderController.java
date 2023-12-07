package com.cafeHi.www.order.controller;

import com.cafeHi.www.cart.dto.CartForm;
import com.cafeHi.www.common.page.PageMaker;
import com.cafeHi.www.common.page.WithoutKeywordCriteria;
import com.cafeHi.www.common.security.service.CustomUser;
import com.cafeHi.www.delivery.dto.DeliveryDTO;
import com.cafeHi.www.member.dto.MembershipForm;
import com.cafeHi.www.menu.dto.MenuDTO;
import com.cafeHi.www.menu.service.MenuService;
import com.cafeHi.www.order.dto.*;
import com.cafeHi.www.order.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    private final MenuService menuService;

    /**
     * 주문 Get Mapping Controller
     */
    @GetMapping("/CafehiOrder")
    public String CafeHiOrderView(@Valid  OrderMenuInfo orderMenuInfo, BindingResult result, Model model) {

         MenuDTO menuDTO = menuService.findMenu(orderMenuInfo.getMenuId());

        if (result.hasErrors()) {
            return "redirect:/menuList/" + orderMenuInfo.getReturnPage();
        }

        if (menuDTO.getMenuStockQuantity() == 0) {
            return "redirect:/menuList/" + orderMenuInfo.getReturnPage() + "?error=stock";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser memberInfo = (CustomUser) principal;

        MembershipForm membershipForm = new MembershipForm(memberInfo.getMemberInfo().getMembership());

        model.addAttribute("Menu", menuDTO);
        model.addAttribute("orderAmount", orderMenuInfo.getToOrderAmount());
        model.addAttribute("Membership", membershipForm);

        return "member/cafehi_memberOrder";
    }

    /**
     * 주문 Post Mapping Controller
     */
    @PostMapping("/CafehiOrder")
    public String CafeHiOrder(OrderRequest orderRequest, DeliveryDTO deliveryDTO,  @RequestParam double membershipNewPoint, Model model) {

        int NewPoint = (int) membershipNewPoint;

        if (deliveryDTO.getDeliveryRoadAddress() == "" || deliveryDTO.getDeliveryDetailAddress() == "") {
            model.addAttribute("msg", "주소를 모두 등록해주세요.");
            model.addAttribute("url", "javascript:history.back()");
            return "common/alert";
        }

        // 주문 로직
        orderService.order(orderRequest, deliveryDTO, NewPoint);

        return "redirect:/CafeHi-MyPageOrderMenuList";
    }

    /**
     * 주문 리스트 Post Mapping Controller
     */
    @PostMapping(path = "/CafehiOrderList", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String CafeHiOrderList(@RequestParam("cartList") String cartListValue, OrderListDTO orderListDTO, DeliveryDTO deliveryDTO, Model model) {

        if (deliveryDTO.getDeliveryRoadAddress() == "" || deliveryDTO.getDeliveryDetailAddress() == "") {
            model.addAttribute("msg", "주소를 모두 등록해주세요.");
            model.addAttribute("url", "javascript:history.back()");
            return "common/alert";
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<CartForm> cartList;

        try {
            cartList = objectMapper.readValue(cartListValue, new TypeReference<List<CartForm>>(){});
        } catch (IOException e) {
            log.info("Error occurred while deserializing cartListJson: {}", e.getMessage());
            return "error";
        }

        // 주문 로직 호출

        orderService.orderList(cartList, orderListDTO, deliveryDTO);

        return "redirect:/CafeHi-MyPageOrderMenuList";
    }


    /**
     * 마이페이지 주문 목록 Get Mapping Controller
     */
    @GetMapping("/CafeHi-MyPageOrderMenuList")
    public String CafehiOrderListView(@ModelAttribute("orderSearch") OrderSearch orderSearch, WithoutKeywordCriteria withoutKeywordCriteria, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUser memberInfo = (CustomUser) principal;

        int offset = withoutKeywordCriteria.getRowStart();
        int limit = withoutKeywordCriteria.getPerPageNum();

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(withoutKeywordCriteria);

        if(memberInfo != null) {

            List<OrderMenuDTO> orderList = orderService.findOrders(limit, offset, withoutKeywordCriteria);
            pageMaker.setTotalCount(orderService.getPagingCount(withoutKeywordCriteria));

            model.addAttribute("orderList", orderList);
            model.addAttribute("pageMaker", pageMaker);
            model.addAttribute("scri", withoutKeywordCriteria);

        }

        return "member/cafehi_myPageOrderList";
    }

    /**
     * 주문 취소 Post Mapping Controller
     */
    @PostMapping("/CancelOrder")
    public String CafehiOrderCancel(@RequestParam String orderMenu_code, @RequestParam int totalOrderPricePoint) {

        orderService.cancelOrder(orderMenu_code, totalOrderPricePoint);

        return "redirect:/CafeHi-MyPageOrderMenuList";
    }



    /**
     * 장바구니 목록, 적립할 포인트, 총 합계 -> 리스트 주문 페이지 redirect Controller
     **/
    @PostMapping(path = "/cartOrderAll", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String CafehicartOrderAll(@RequestParam("cartList") String cartListValue, @RequestParam("totalPoint") Double totalPoint, @RequestParam("sumMoney") int sumMoney, RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("cartList", cartListValue);
        redirectAttributes.addAttribute("totalPoint", totalPoint);
        redirectAttributes.addAttribute("sumMoney" , sumMoney);

        return "redirect:/CafehiOrderList";
    }


    /**
     * /cartOrderAll PostMapping -> /CafehiOrderList GetMapping Controller (POST REDIRECT GET 방식)
     **/
    @GetMapping("/CafehiOrderList")
    public String CafehicartOrderListView(@RequestParam("cartList") String cartListValue, @RequestParam("totalPoint") Double totalPoint, @RequestParam("sumMoney") int sumMoney, Model model) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<CartForm> cartList;

        try {
            cartList = objectMapper.readValue(cartListValue, new TypeReference<List<CartForm>>(){});
        } catch (IOException e) {
            log.info("Error occurred while deserializing cartListJson: {}", e.getMessage());
            return "error";
        }

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPoint", totalPoint);
        model.addAttribute("sumMoney", sumMoney);

        return "member/cafehi_memberOrderList";

    }


}
