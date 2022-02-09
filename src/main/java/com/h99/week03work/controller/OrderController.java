package com.h99.week03work.controller;

import com.h99.week03work.dtoOrder.FoodOrderDto;
import com.h99.week03work.dtoOrder.OrderDto;
import com.h99.week03work.dtoOrder.OrderRequestDto;
import com.h99.week03work.model.Orders;
import com.h99.week03work.repository.FoodRepository;
import com.h99.week03work.repository.OrderRepository;
import com.h99.week03work.repository.RestaurantRepository;
import com.h99.week03work.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    @PostMapping("/order/request")
    public OrderDto newOrder(@RequestBody OrderRequestDto requestDto){
        OrderDto orderDto = orderService.registerOrder(requestDto);
        return orderDto;
    }

    @GetMapping("/orders")
    public List<OrderDto> getAllOrders(){
        //주문서를 모두 꺼내온다
        List<Orders> orderList = orderRepository.findAll();
//        for(int i = 0; i < orderList.size(); i++){
//            System.out.println(orderList.get(i).getId());
//            System.out.println(orderList.get(i).getQuantity());
//        }
        //음식점 id 찾기
        Long reId = orderList.get(0).getRestaurantId();
        //음식점 이름 꺼내기
        String reName = restaurantRepository.getById(reId).getName();
        //음식점 배달비 꺼내기
        int deliveryFee = restaurantRepository.getById(reId).getDeliveryFee();
        System.out.println(deliveryFee);
        //음식가격을 위한 값
        int sum = 0;
        ArrayList<FoodOrderDto> foodList = new ArrayList<FoodOrderDto>();
        for(int i = 0; i < orderList.size(); i++){
            //주문수량
            int quantity = orderList.get(i).getQuantity();
            System.out.println(quantity);
            //해당 음식명 빼기
            String foodName = foodRepository.getById(orderList.get(i).getFoodId()).getName();
            System.out.println(foodName);
            //해당 음식 가격 빼기
            int price = foodRepository.getById(orderList.get(i).getFoodId()).getPrice();
            //해당 음식 총 가격
            int foodPrice = quantity * price;
            System.out.println(foodPrice);
            //전체 가격을 위한 합
            sum += foodPrice;
            //리스트에 넣어주기위해 dto생성
            FoodOrderDto foodOrderDto = new FoodOrderDto(foodName, quantity, foodPrice);
            foodList.add(foodOrderDto);
        }
        //배달비와 총 음식가격을 합한것
        int totalPrice = sum + deliveryFee;
        OrderDto orderDtoList = new OrderDto(reName, foodList, deliveryFee, totalPrice);
        System.out.println(orderDtoList.getRestaurantName());
        System.out.println(totalPrice);
        List<OrderDto> result = new ArrayList<>();
        result.add(orderDtoList);
        return result;
    }
}
