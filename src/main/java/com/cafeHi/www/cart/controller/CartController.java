package com.cafeHi.www.cart.controller;

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
	public String myPageCartView(Model model) {
		
		Map<String, Object> myPageCartMap = new ConcurrentHashMap<String, Object>();
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    CustomUser userInfo = (CustomUser)principal;
	    Long member_code = userInfo.getMemberDTO().getMember_code();
		
	    if (member_code != 0) {
			List<CartDTO> getCartListDTO = cartService.getCartList(member_code); // 장바구니 목록
			int sumMoney = cartService.sumMoney(member_code); // 금액 합계
			// 배송료 계산
			// 30000원이 넘으면 배송료가 0원, 안넘으면 2500원
			int fee=sumMoney >= 30000? 0 : 2500;
			// hash map 장바구니에 넣을 각종 값들을 저장한다.
			myPageCartMap.put("sumMoney", sumMoney);
			myPageCartMap.put("fee", fee); //배송료
			myPageCartMap.put("sum", sumMoney+fee); // 전체 금액
			myPageCartMap.put("CartList", getCartListDTO); // 장바구니 목록
			myPageCartMap.put("CartListSize", getCartListDTO.size()); // 레코드 개수
			
			model.addAttribute("myPageCartMap", myPageCartMap);
		}
	    
	    
		return "member/cafehi_myPageCart";
	}
	
	
	@PostMapping("/insertCart")
	public String CartInsert(@AuthenticationPrincipal CustomUser customUser, @RequestParam int toCartAmount, CartDTO cartDTO, HttpServletRequest request) {
		
		if(toCartAmount == 0) {
			request.setAttribute("msg", "수량은 1개 이상 담을 수 있습니다.");
			request.setAttribute("url", "coffeeList");
			System.out.println("toCartAmount : " + toCartAmount);
			
			return "common/alert";
		}
				
		
		cartDTO.setCart_amount(toCartAmount);

//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		CustomUser userInfo = (CustomUser) principal; 		
//		CustomUser 강제 캐스팅 하는 방식에서 java.lang.classcastexception; 에러가 발생했다. 
//		구글 서치를 통해 @AuthenticationPrincipal를 이용하여 CustomUser 객체를 인자에 넘겨주는 방식을 사용하여 해결했다. 
		

		Long member_code = customUser.getMemberDTO().getMember_code();
		cartDTO.setMember_code(member_code);
		cartDTO.setCart_writetime(LocalDateTime.now());
		cartDTO.setCart_updatetime(LocalDateTime.now());
		
		cartService.insertCart(cartDTO);
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
