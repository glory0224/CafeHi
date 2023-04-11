package com.cafeHi.www.cart.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.cafeHi.www.cart.dto.CartDTO;
import com.cafeHi.www.cart.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafeHi.www.member.dto.CustomUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	@GetMapping("/CafeHi-MyPageCart")
	public String myPageCartView(Model model, Principal principal) {

		if (principal != null) {
			String username = principal.getName();
			CustomUser userInfo = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long memberCode = userInfo.getMemberDTO().getMember_code();
			Map<String, Object> myPageCartMap = cartService.getCartInfo(memberCode);
			model.addAttribute("myPageCartMap", myPageCartMap);
		}
	    
		return "member/cafehi_myPageCart";
	}
	
	
	@PostMapping("/insertCart")
	public String CartInsert(@AuthenticationPrincipal CustomUser customUser, @RequestParam int toCartAmount, CartDTO cartDTO, HttpServletRequest request) {
		
		if(toCartAmount == 0) {
			request.setAttribute("msg", "수량은 1개 이상 담을 수 있습니다.");
			request.setAttribute("url", "coffeeList");

			return "common/alert";
		}

		//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		CustomUser userInfo = (CustomUser) principal;
//		CustomUser 강제 캐스팅 하는 방식에서 java.lang.classcastexception; 에러가 발생했다.
//		구글 서치를 통해 @AuthenticationPrincipal를 이용하여 CustomUser 객체를 인자에 넘겨주는 방식을 사용하여 해결했다.

		try {
			Long member_code = customUser.getMemberDTO().getMember_code();
			cartService.insertCart(member_code, toCartAmount, cartDTO);
		}catch (Exception e) {

		}


		return "redirect:/CafeHi-MyPageCart";
		
	}
	
	
	// 장바구니 메뉴 항목 삭제 
	@PostMapping("/deleteCart")
	public String CartDelete(CartDTO cartDTO) {
		
		cartService.deleteCart(cartDTO.getCart_code());
		
		return "redirect:/CafeHi-MyPageCart";
	}
	
	// 장바구니 수정 
		@PostMapping("/modifyCart")
		public String CartModify(CartDTO cartDTO) {
					
			cartService.modifyCart(cartDTO);
			
			return "redirect:/CafeHi-MyPageCart";
			
		}
	
	// 장바구니 비우기
		@PostMapping("/deleteCartAll")
		public String CartDeleteAll() {
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			Long member_code = userInfo.getMemberDTO().getMember_code();
			
			
			cartService.deleteAllCart(member_code);
			
			return "redirect:/CafeHi-MyPageCart";
			
		}
	
	
	
	
}
