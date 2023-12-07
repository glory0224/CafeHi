package com.cafeHi.www.cart.controller;

import com.cafeHi.www.cart.dto.CartForm;
import com.cafeHi.www.cart.dto.ModifyCartForm;
import com.cafeHi.www.cart.service.CartService;
import com.cafeHi.www.common.security.service.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {
	
	private final CartService cartService;
	
	@GetMapping("/CafeHi-MyPageCart")
	public String myPageCartView(Model model, Principal principal) {

		if (principal != null) {

			CustomUser memberInfo = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long memberCode = memberInfo.getMemberInfo().getMemberCode();



			List<CartForm> cartList = cartService.findCartList(memberCode);

			int sumMoney = 0;

			for (CartForm cartForm : cartList) {
				// 장바구니에 담긴 메뉴와 개수를 계산한 비용을 총 비용 변수에 더해준다.
				sumMoney += cartForm.getCartAmount() * cartForm.getMenuDTO().getMenuPrice();
			}

			// 합계에 대한 포인트 계산
			double totalPoint = cartService.CalculateCartPoint(sumMoney, memberCode);

			model.addAttribute("sumMoney", sumMoney);
			model.addAttribute("totalPoint", totalPoint);
			model.addAttribute("cartList", cartList);
			model.addAttribute("memberCode", memberCode);
		}

		return "member/cafehi_myPageCart";
	}
	
	
	@PostMapping("/insertCart")
	public String CartInsert(@AuthenticationPrincipal CustomUser customUser, @RequestParam int toCartAmount, @RequestParam Long menuId, HttpServletRequest request) {
		
		if(toCartAmount == 0) {
			request.setAttribute("msg", "수량은 1개 이상 담을 수 있습니다.");
			request.setAttribute("url", "javascript:history.back()");

			return "common/alert";
		}

		cartService.insertCart(customUser.getMemberInfo().getMemberCode(),menuId, toCartAmount);

		//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		CustomUser userInfo = (CustomUser) principal;
//		CustomUser 강제 캐스팅 하는 방식에서 java.lang.classcastexception; 에러가 발생했다.
//		구글 서치를 통해 @AuthenticationPrincipal를 이용하여 CustomUser 객체를 인자에 넘겨주는 방식을 사용하여 해결했다.

		return "redirect:/CafeHi-MyPageCart";
		
	}
	
	
	// 장바구니 메뉴 항목 삭제
	@PostMapping("/deleteCart")
	public String CartDelete(@RequestParam Long cart_code) {

		cartService.deleteCart(cart_code);

		return "redirect:/CafeHi-MyPageCart";
	}

	// 장바구니 수정
		@PostMapping("/modifyCart")
		public String CartModify(ModifyCartForm cartForm) {

			cartService.modifyCart(cartForm);

			return "redirect:/CafeHi-MyPageCart";

		}

	// 장바구니 비우기
		@PostMapping("/deleteCartAll")
		public String CartDeleteAll() {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CustomUser userInfo = (CustomUser) principal;
			Long member_code = userInfo.getMemberInfo().getMemberCode();


			cartService.deleteAllCart(member_code);

			return "redirect:/CafeHi-MyPageCart";

		}
	
	
	
	
}
