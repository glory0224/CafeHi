package com.cafeHi.www.cart.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafeHi.www.cart.dto.Cart;
import com.cafeHi.www.mapper.cart.CartMapper;
import com.cafeHi.www.member.dto.CustomUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final CartMapper cartMapper;
	
	@GetMapping("/CafeHi-MyPageCart")
	public String myPageCartView(Model model) {
		
		Map<String, Object> myPageCartMap = new ConcurrentHashMap<String, Object>();
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    CustomUser userInfo = (CustomUser)principal;
	    int member_code = userInfo.getMember().getMember_code();
		
	    if (member_code != 0) {
			List<Cart> getCartList = cartMapper.getCartList(member_code); // 장바구니 목록
			int sumMoney = cartMapper.sumMoney(member_code); // 금액 합계
			// 배송료 계산
			// 30000원이 넘으면 배송료가 0원, 안넘으면 2500원
			int fee=sumMoney >= 30000? 0 : 2500;
			// hash map 장바구니에 넣을 각종 값들을 저장한다.
			myPageCartMap.put("sumMoney", sumMoney);
			myPageCartMap.put("fee", fee); //배송료
			myPageCartMap.put("sum", sumMoney+fee); // 전체 금액
			myPageCartMap.put("CartList", getCartList); // 장바구니 목록
			myPageCartMap.put("CartListSize", getCartList.size()); // 레코드 개수
			
			model.addAttribute("myPageCartMap", myPageCartMap);
		}
	    
	    
		return "member/cafehi_myPageCart";
	}
	
	
	@PostMapping("/insertCart")
	public String CartInsert(@AuthenticationPrincipal CustomUser customUser, @RequestParam int toCartAmount, Cart cart, HttpServletRequest request) {
		
		if(toCartAmount == 0) {
			request.setAttribute("msg", "수량은 1개 이상 담을 수 있습니다.");
			request.setAttribute("url", "coffeeList");
			System.out.println("toCartAmount : " + toCartAmount);
			
			return "common/alert";
		}
				
		
		cart.setCart_amount(toCartAmount);

//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		CustomUser userInfo = (CustomUser) principal; 		
//		CustomUser 강제 캐스팅 하는 방식에서 java.lang.classcastexception; 에러가 발생했다. 
//		구글 서치를 통해 @AuthenticationPrincipal를 이용하여 CustomUser 객체를 인자에 넘겨주는 방식을 사용하여 해결했다. 
		

		int member_code = customUser.getMember().getMember_code();
		cart.setMember_code(member_code);
		cart.setCart_writetime(LocalDateTime.now());
		cart.setCart_updatetime(LocalDateTime.now());
		
		cartMapper.insertCart(cart);
		return "redirect:/CafeHi-MyPageCart";
		
	}
	
	
	// 장바구니 메뉴 항목 삭제 
	@PostMapping("/deleteCart")
	public String CartDelete(Cart cart) {
		
		cartMapper.deleteCart(cart.getCart_code());
		
		return "redirect:/CafeHi-MyPageCart";
	}
	
	// 장바구니 수정 
		@PostMapping("/modifyCart")
		public String CartModify(Cart cart) {
					
			cartMapper.modifyCart(cart);
			
			return "redirect:/CafeHi-MyPageCart";
			
		}
	
	// 장바구니 비우기
		@PostMapping("/deleteCartAll")
		public String CartDeleteAll() {
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			int member_code = userInfo.getMember().getMember_code();
			
			
			cartMapper.deleteAllCart(member_code);
			
			return "redirect:/CafeHi-MyPageCart";
			
		}
	
	
	
	
}
