package com.cafeHi.www.order.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafeHi.www.mapper.member.MembershipMapper;
import com.cafeHi.www.mapper.menu.MenuMapper;
import com.cafeHi.www.mapper.order.OrderMapper;
import com.cafeHi.www.mapper.order.OrderMenuMapper;
import com.cafeHi.www.member.dto.CustomUser;
import com.cafeHi.www.member.dto.Membership;
import com.cafeHi.www.member.service.MembershipService;
import com.cafeHi.www.menu.dto.Menu;
import com.cafeHi.www.order.dto.OrderMenu;
import com.cafeHi.www.order.dto.Orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	
	private final MenuMapper menuMapper;
	
	private final MembershipService membershipService;
	
	private final OrderMapper orderMapper;
	
	private final OrderMenuMapper orderMenuMapper;
	
	
	@GetMapping("/CafehiOrder")
	public String CafeHiOrderView(@RequestParam(required = false) int toOrderAmount, Menu menu, Model model, HttpServletRequest request) {
		
		// 수량 검증 로직
		if(toOrderAmount == 0) {
			request.setAttribute("msg", "수량을 선택 해야 구매 가능합니다.");
			request.setAttribute("url", "history.back()");
			
			return "common/goBackAlert"; // 이전페이지 이동
		}
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    CustomUser userInfo = (CustomUser)principal;
	    int member_code = userInfo.getMember().getMember_code();
		
	    
		model.addAttribute("Menu", menuMapper.getMenu(menu.getMenu_code()));
		model.addAttribute("orderAmount", toOrderAmount);
		model.addAttribute("Membership", membershipService.getMembership(member_code));
		
		return "member/cafehi_memberOrder";
		
	    
	}
	
	@PostMapping("/CafehiOrder")
	public String CafeHiOrder(
			@RequestParam(value="deliveryFee") int deliveryFee,
			@RequestParam("menu_code") int menu_code,
			@RequestParam("include_delivery") Boolean include_delivery,
			@RequestParam("member_code") int member_code,
			@RequestParam("total_order_count") int total_order_count,
			@RequestParam("membership_point") int membership_point,
			@RequestParam("membership_new_point") double membership_new_point
			)  {
		
		Orders newOrder = new Orders();
		OrderMenu newOrderMenu = new OrderMenu();
		Membership newMembership = new Membership();


		newOrder.setOrderInfo(member_code, include_delivery);

		log.info("membership_point = {}", membership_point);
		log.info("membership_new_point = {}", membership_new_point);

		newMembership.setMembershipPointInfo(member_code, membership_point, membership_new_point);

		
		Menu getMenu = menuMapper.getMenu(menu_code);
		
		if (deliveryFee != 0 & newOrder.getInclude_delivery()) {
			
			orderMapper.insertOrder(newOrder);			
			
			int total_order_price = newOrderMenu.CalTotalPrice(deliveryFee, getMenu.getMenu_price(), total_order_count); // 배송비 포함 총 비용
			
			newOrderMenu.setMenu(getMenu);

			newOrderMenu.setOrderMenuInfo(membership_new_point, total_order_price, total_order_count);


			orderMenuMapper.insertOrderMenu(newOrderMenu);

			// 주문 포인트 적립
			membershipService.updateMembershipPoint(newMembership);
			
			return "redirect:/CafeHi-MyPageOrderMenuList";
			
		} else {
			
			orderMapper.insertOrder(newOrder);
			
			int total_order_price = newOrderMenu.CalTotalPrice(getMenu.getMenu_price(), total_order_count); // 배송비 미포함 총 비용
			
			newOrderMenu.setMenu(getMenu);

			newOrderMenu.setOrderMenuInfo(membership_new_point, total_order_price, total_order_count);
				
			orderMenuMapper.insertOrderMenu(newOrderMenu);

			// 주문 포인트 적립
			membershipService.updateMembershipPoint(newMembership);
			
			return "redirect:/CafeHi-MyPageOrderMenuList";
		}
		
		
	}
	
	
	@GetMapping("/CafeHi-MyPageOrderMenuList")
	public String CafehiOrderListView(Model model) {
		
		log.info("CafehiOrderList Controller!!");
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUser userInfo = (CustomUser)principal;
		int member_code = userInfo.getMember().getMember_code();
		
		// 0으로 체크하는 것이 맞나?
		// primitive type 은  null 이 들어올 수 없기 때문에 0으로 체크하는 것이 옳은가?
		if(member_code != 0) {
			List<OrderMenu> orderMenuList = orderMenuMapper.findOrderMenuList(member_code);
			
			log.info("orderMenuListSize = {}", orderMenuList.size());
			
			for (OrderMenu orderMenu : orderMenuList) {
				log.info("orderMenu.order.order_code = {}", orderMenu.getOrder().getOrder_code());
			}
				
			
			model.addAttribute("OrderMenuList", orderMenuList);
			model.addAttribute("OrderMenuListCount", orderMenuList.size());
			
		}
		
		
		return "member/cafehi_myPageOrderList";
		
	}
	
	
	@PostMapping("/CancelOrder")
	public String CafehiOrderCancel(Orders order,  Model model) {
		order.cancelTimeAndStatus();
		log.info("order_code = {}", order.getOrder_code());
		log.info("order_updatetime = {}", order.getOrder_updatetime());
		log.info("order_status = {}", order.getOrder_status());
		
		orderMapper.cancelOrder(order);
		
		
		
		return "redirect:/CafeHi-MyPageOrderMenuList";
	}
 }
