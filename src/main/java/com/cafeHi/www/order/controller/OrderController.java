package com.cafeHi.www.order.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cafeHi.www.member.dto.MembershipDTO;
import com.cafeHi.www.menu.service.MenuService;
import com.cafeHi.www.order.dto.OrderMenuDTO;
import com.cafeHi.www.order.dto.OrdersDTO;
import com.cafeHi.www.order.service.OrderMenuService;
import com.cafeHi.www.order.service.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafeHi.www.member.dto.CustomUser;
import com.cafeHi.www.member.service.MembershipService;
import com.cafeHi.www.menu.dto.MenuDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	
	private final MenuService menuService;
	
	private final MembershipService membershipService;
	
	private final OrderService orderService;
	
	private final OrderMenuService orderMenuService;
	
	
	@GetMapping("/CafehiOrder")
	public String CafeHiOrderView(@RequestParam(required = false) Integer toOrderAmount, MenuDTO menuDTO, Model model, HttpServletRequest request) {

		// 재고가 다 소진된 경우
		if (menuDTO.getMenu_stock_quantity() == 0) {
			request.setAttribute("msg", "재고가 다 소진되었습니다.");
			return "common/goBackAlert"; // 이전페이지 이동
		}

		// 수량이 null로 넘어오는 경우
		if(toOrderAmount == null) {
			request.setAttribute("msg", "수량이 선택되지 않았습니다. 메뉴 페이지로 돌아갑니다.");
			request.setAttribute("url", "/CafeHi-Menu");

			return "common/alert";
		}

		// 수량 검증 로직
		if(toOrderAmount == 0) {
			request.setAttribute("msg", "수량을 선택 해야 구매 가능합니다.");
			return "common/goBackAlert"; // 이전페이지 이동
		}
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    CustomUser userInfo = (CustomUser)principal;
	    Long member_code = userInfo.getMemberDTO().getMember_code();
		

		model.addAttribute("Menu", menuService.getMenu(menuDTO.getMenu_code()));
		model.addAttribute("orderAmount", toOrderAmount);
		model.addAttribute("Membership", membershipService.getMembership(member_code));
		
		return "member/cafehi_memberOrder";
		
	    
	}
	
	@PostMapping("/CafehiOrder")
	public String CafeHiOrder(
			@RequestParam(value="deliveryFee") int deliveryFee,
			@RequestParam("menu_code") Long menu_code,
			@RequestParam("include_delivery") Boolean include_delivery,
			@RequestParam("member_code") Long member_code,
			@RequestParam("total_order_count") int total_order_count,
			@RequestParam("membership_point") int membership_point,
			@RequestParam("membership_new_point") double membership_new_point
			)  {
		
		OrdersDTO newOrder = new OrdersDTO();
		OrderMenuDTO newOrderMenuDTO = new OrderMenuDTO();
		MembershipDTO newMembershipDTO = new MembershipDTO();


		newOrder.setOrderInfo(member_code, include_delivery);

		newMembershipDTO.setMembershipPointInfo(member_code, membership_point, membership_new_point);

		// 트랜잭션 적용을 위한 인터페이스 도입
		
		MenuDTO getMenuDTO = menuService.getMenu(menu_code);

		menuService.DecreaseMenuStockQuantity(getMenuDTO, total_order_count);


		if (deliveryFee != 0 & newOrder.getInclude_delivery()) {
			
			orderService.insertOrder(newOrder);
			
			int total_order_price = newOrderMenuDTO.CalTotalPrice(deliveryFee, getMenuDTO.getMenu_price(), total_order_count); // 배송비 포함 총 비용
			
			newOrderMenuDTO.setMenuDTO(getMenuDTO);

			newOrderMenuDTO.setOrderMenuInfo(membership_new_point, total_order_price, total_order_count);


			orderMenuService.insertOrderMenu(newOrderMenuDTO);

			// 주문 포인트 적립
			membershipService.updateMembershipPoint(newMembershipDTO);
			
			return "redirect:/CafeHi-MyPageOrderMenuList";
			
		} else {
			
			orderService.insertOrder(newOrder);
			
			int total_order_price = newOrderMenuDTO.CalTotalPrice(getMenuDTO.getMenu_price(), total_order_count); // 배송비 미포함 총 비용
			
			newOrderMenuDTO.setMenuDTO(getMenuDTO);

			newOrderMenuDTO.setOrderMenuInfo(membership_new_point, total_order_price, total_order_count);
				
			orderMenuService.insertOrderMenu(newOrderMenuDTO);

			// 주문 포인트 적립
			membershipService.updateMembershipPoint(newMembershipDTO);
			
			return "redirect:/CafeHi-MyPageOrderMenuList";
		}
		
		
	}
	
	
	@GetMapping("/CafeHi-MyPageOrderMenuList")
	public String CafehiOrderListView(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUser userInfo = (CustomUser)principal;
		Long member_code = userInfo.getMemberDTO().getMember_code();
		
		// 0으로 체크하는 것이 맞나?
		// primitive type 은  null 이 들어올 수 없기 때문에 0으로 체크하는 것이 옳은가?
		if(member_code != 0) {
			List<OrderMenuDTO> orderMenuDTOList = orderMenuService.findOrderMenuList(member_code);

			model.addAttribute("OrderMenuList", orderMenuDTOList);
			model.addAttribute("OrderMenuListCount", orderMenuDTOList.size());
			
		}
		
		
		return "member/cafehi_myPageOrderList";
		
	}
	
	
	@PostMapping("/CancelOrder")
	public String CafehiOrderCancel(OrdersDTO order, OrderMenuDTO orderMenuDTO, MenuDTO menuDTO) {
		order.cancelTimeAndStatus();

		orderService.cancelOrder(order);

		orderMenuDTO.cancelTimeAndStatus();

		orderMenuService.cancelOrderMenu(orderMenuDTO);

		MenuDTO getMenuDTO = menuService.getMenu(menuDTO.getMenu_code());

		menuService.IncreaseMenuStockQuantity(getMenuDTO, orderMenuDTO.getTotal_order_count());

		// 세션 값
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUser userInfo = (CustomUser)principal;
		Long member_code = userInfo.getMemberDTO().getMember_code();

		MembershipDTO getMembershipDTO = membershipService.getMembership(member_code);

		membershipService.minusMembershipPoint(getMembershipDTO, orderMenuDTO.getTotal_order_price_point());
		
		return "redirect:/CafeHi-MyPageOrderMenuList";
	}
 }
