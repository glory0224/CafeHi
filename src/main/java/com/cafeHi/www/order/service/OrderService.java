package com.cafeHi.www.order.service;

import com.cafeHi.cart.dto.CartForm;
import com.cafeHi.cart.repository.CartRepository;
import com.cafeHi.common.page.WithoutKeywordCriteria;
import com.cafeHi.delivery.dto.DeliveryDTO;
import com.cafeHi.delivery.entity.Delivery;
import com.cafeHi.delivery.repository.DeliveryRepository;
import com.cafeHi.member.dto.CustomMember;
import com.cafeHi.member.entity.Member;
import com.cafeHi.member.entity.Membership;
import com.cafeHi.member.repository.MemberRepository;
import com.cafeHi.member.repository.MembershipRepository;
import com.cafeHi.menu.dto.MenuDTO;
import com.cafeHi.menu.entity.Menu;
import com.cafeHi.menu.repository.MenuRepository;
import com.cafeHi.order.dto.OrderDTO;
import com.cafeHi.order.dto.OrderListDTO;
import com.cafeHi.order.dto.OrderMenuDTO;
import com.cafeHi.order.dto.OrderRequest;
import com.cafeHi.order.entity.Order;
import com.cafeHi.order.entity.OrderMenu;
import com.cafeHi.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final MenuRepository menuRepository;
    private final DeliveryRepository deliveryRepository;

    private final CartRepository cartRepository;



    /**
     * 주문
     */
    @Transactional
    public Long order(OrderRequest orderRequest, DeliveryDTO deliveryDTO, int membershipNewPoint) {

        // 엔티티 조회
        Member member = memberRepository.findMember(orderRequest.getMemberCode());
        Menu menu = menuRepository.findMenu(orderRequest.getMenuId());

        String orderMenuCode = generateOrderCode();

        // 주문메뉴 생성
        OrderMenu orderMenu = OrderMenu.createOrderMenu(menu, orderRequest.getTotalOrderPrice() + deliveryDTO.getDeliveryFee() , orderRequest.getTotalOrderCount(), membershipNewPoint, orderMenuCode);

        // 배송 주소 생성
        Delivery delivery = Delivery.createDelivery(deliveryDTO);

        // 배송 주소 저장
        deliveryRepository.saveDelivery(delivery);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderMenu);

        // 주문 저장
        orderRepository.saveOrder(order);

        // 주문 메뉴 저장
        orderRepository.saveOrderMenu(orderMenu);

        // 포인트 적립
        Membership membership = membershipRepository.findMembershipbByMemberId(member);

        membership.updateMembership(membershipNewPoint);
        // 등급 갱신
        membership.updateMemberGrade(membership.getMembershipPoint());


       // 세션 정보 갱신
        updateSession(membership);

        return order.getId();
    }

    /**
     * 장바구니 리스트 주문 리스트에 추가
     */
    @Transactional
    public void orderList(List<CartForm> cartList , OrderListDTO orderListDTO , DeliveryDTO deliveryDTO) {

        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 정보 업데이트
        CustomMember customMember = (CustomMember) authentication.getPrincipal();

        // 세션에서 멤버 키 가져와서 멤버 엔티티 가져옴
        Member member = memberRepository.findMember(customMember.getMemberInfo().getMemberCode());

        // 주문 코드 생성
        String orderMenuCode = generateOrderCode();

        // 카트의 길이만큼 저장
        for (CartForm cartForm : cartList){
            Menu menu = menuRepository.findMenu(cartForm.getMenuDTO().getMenuId());

            // 주문메뉴 생성
            // 각 메뉴에 대한 포인트가 저장되야함 - 현재는 장바구니 전체의 포인트가 넘어와서 주문 리스트에서 보여질때 전체 포인트로 잘못 보여지는 문제

            OrderMenu orderMenu = OrderMenu.createOrderMenu(menu, menu.getMenuPrice() * cartForm.getCartAmount(), cartForm.getCartAmount(), (int) orderListDTO.getMembershipPoint(), orderMenuCode);

            // 배송 주소 생성
            Delivery delivery = Delivery.createDelivery(deliveryDTO);

            // 배송 주소 저장
            deliveryRepository.saveDelivery(delivery);

            // 주문 생성
            Order order = Order.createOrder(member, delivery, orderMenu);

            // 주문 저장
            orderRepository.saveOrder(order);

            // 주문 메뉴 저장
            orderRepository.saveOrderMenu(orderMenu);

        }

        // 장바구니 비우기

        cartRepository.deleteAllCart(customMember.getMemberInfo().getMemberCode());

        // 포인트 적립
        Membership membership = membershipRepository.findMembershipbByMemberId(member);

        membership.updateMembership(orderListDTO.getMembershipPoint());
        // 등급 갱신
        membership.updateMemberGrade(membership.getMembershipPoint());

        // 세션 정보 갱신
        updateSession(membership);

    }


    /**
     * 멤버쉽 세션 업데이트
     */
    private void updateSession(Membership membership) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 정보 업데이트
        CustomMember customMember = (CustomMember) authentication.getPrincipal();
        customMember.getMemberInfo().setMembership(membership);

        // 인증 객체 업데이트
        Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(customMember, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(String orderCode, int totalOrderPricePoint) {

        boolean isSingleOrder = orderRepository.isSingleOrder(orderCode);

        if (isSingleOrder) {
            // 단일 주문을 취소하는 경우
            OrderMenu orderMenu = orderRepository.findOrderByOrderCode(orderCode);
            performCancelOrder(orderMenu, totalOrderPricePoint);
        }else {
            // 여러 주문을 취소하는 경우
            List<OrderMenu> orderMenuList = orderRepository.findOrderListByOrderCode(orderCode);
            // flag 변수를 사용해서 전체 포인트를 한번만 빼준다.
            boolean firstIteration = true;

            for (OrderMenu orderMenu : orderMenuList) {
                if (firstIteration) {
                    performCancelOrder(orderMenu, totalOrderPricePoint); // 이 주문으로 적립된 포인트를 1번만 빼주고
                    firstIteration = false;
                } else {
                    performCancelOrder(orderMenu, 0); // 나머지는 0으로 뺀다.
                }
            }
        }
    }

    /**
     * Order의 주문 취소 및 멤버쉽 포인트 되돌림
     */
    private void performCancelOrder(OrderMenu orderMenu, int totalOrderPricePoint) {

        // 주문 엔티티 조회
        Order order = orderMenu.getOrder();
        // 주문 취소
        order.cancel();

        Member member = order.getMember();
        Membership membership = member.getMembership();

        membership.minusMembershipPoint(totalOrderPricePoint);

        membership.updateMemberGrade(membership.getMembershipPoint());

        updateSession(membership);
    }


    /**
     * 주문 목록 찾기
     */
    public List<OrderMenuDTO> findOrders(int limit, int offset, WithoutKeywordCriteria withoutKeywordCriteria) {

        List<OrderMenu> orderMenuList = orderRepository.findAll(limit, offset, withoutKeywordCriteria);
        List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();

        for (OrderMenu orderMenu : orderMenuList) {

            // 객체를 이제 Service 로직에서 DTO 타입으로 변환해서 반환한다.
            // orderMenuDTO, orderDTO, memberDTO, deliveryDTO, menuDTO

            MenuDTO menuDTO = new MenuDTO(orderMenu.getMenu());
            OrderDTO orderDTO = new OrderDTO(orderMenu.getOrder());
            DeliveryDTO deliveryDTO = new DeliveryDTO(orderMenu.getOrder().getDelivery());
            OrderMenuDTO orderMenuDTO = new OrderMenuDTO(orderMenu, menuDTO, orderDTO, deliveryDTO);
            orderMenuDTOList.add(orderMenuDTO);
        }


        return orderMenuDTOList;

    }

    /**
     * 총 주문 개수 카운트
     */
    public int getPagingCount(WithoutKeywordCriteria withoutKeywordCriteria) {

        return orderRepository.getPagingCount(withoutKeywordCriteria);
    }

    /**
     * 주문 코드를 생성하는 비지니스 로직
     */
    private String generateOrderCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
}
