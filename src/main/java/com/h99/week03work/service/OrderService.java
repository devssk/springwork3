package com.h99.week03work.service;

import com.h99.week03work.dtoOrder.*;
import com.h99.week03work.model.Orders;
import com.h99.week03work.repository.FoodRepository;
import com.h99.week03work.repository.OrderRepository;
import com.h99.week03work.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;

    public OrderDto registerOrder(OrderRequestDto requestDto){
        //레스토랑이름꺼내기
        String reName = restaurantRepository.findById(requestDto.getRestaurantId()).get().getName();
        //해당 음식점 배달비 꺼내기
        int deliveryFee = restaurantRepository.findById(requestDto.getRestaurantId()).get().getDeliveryFee();
        //해당 음식점 최소 주문금액 꺼내기
        int minOrderPrice = restaurantRepository.findById(requestDto.getRestaurantId()).get().getMinOrderPrice();
        //주문서에서 주문리스트 꺼내기
        List<FoodOrderRequestDto> foods = requestDto.getFoods();
        //음식값 총합
        int sum = 0;
        //주문리스트
        ArrayList<FoodOrderDto> foodList = new ArrayList<FoodOrderDto>();
        //반복문 주문갯수만큼
        for(int i = 0; i < foods.size(); i++){
            //음식명 꺼내기
            String foodName = foodRepository.getById(foods.get(i).getId()).getName();
            //주문 수량
            int quantity = foods.get(i).getQuantity();
            //주문 수량 체크
            if(quantity < 1 || quantity > 100){
                throw new IllegalArgumentException("주문 수량은 1 ~ 100 사이입니다.");
            }
            //해당 음식 가격 꺼내기
            int price = foodRepository.getById(foods.get(i).getId()).getPrice();
            //해당 음식 총 가격
            int foodPrice = quantity * price;
            //전체 가격을 위한 합
            sum += foodPrice;
            //Dto만들어서 주문리스트에 넣어주기
            FoodOrderDto foodsOrderDto = new FoodOrderDto(foodName, quantity, foodPrice);
            foodList.add(foodsOrderDto);
        }
        //주문 금액 체크
        if(sum < minOrderPrice){
            throw new IllegalArgumentException("주문 금액이 최소 주문 금액보다 작습니다.");
        }
        for (int i = 0; i < foods.size(); i++) {
            OrderCheckDto orderCheckDto = new OrderCheckDto();
            orderCheckDto.setRestaurantId(requestDto.getRestaurantId());
            orderCheckDto.setFoodId(foods.get(i).getId());
            orderCheckDto.setQuantity(foods.get(i).getQuantity());
            Orders orderList = new Orders(orderCheckDto);
            orderRepository.save(orderList);
        }
        int totalPrice = sum + deliveryFee;
        OrderDto orderDto = new OrderDto(reName, foodList, deliveryFee, totalPrice);
        return orderDto;
    }

}
