package com.h99.week03work.service;

import com.h99.week03work.dto.RestaurantRequestDto;
import com.h99.week03work.model.Restaurant;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {


    public Restaurant registerRestaurant(RestaurantRequestDto requestDto){
        //주문값 체크
        //허용값: 1,000원 ~ 100,000원
        if(requestDto.getMinOrderPrice() < 1000 || requestDto.getMinOrderPrice() > 100000){
            throw new IllegalArgumentException("1,000원에서 100,000원 사이만 입력 가능합니다.");
        }
        //100 원 단위로만 입력 가능 (예. 2,220원 입력 시 에러발생. 2,300원은 입력 가능)
        if(requestDto.getMinOrderPrice()%100 != 0){
            throw new IllegalArgumentException("100원 단위로만 입력이 가능합니다.");
        }
        //배달비 체크
        //0원에서 10,000원까지
        if(requestDto.getDeliveryFee() < 0 || requestDto.getDeliveryFee() > 10000){
            throw new IllegalArgumentException("0원에서 10,000원 사이만 입력 가능합니다.");
        }
        //1000원보다 작을때는 0원과 500원이 아니면 안됨
        if(requestDto.getDeliveryFee() < 1000){
            if(requestDto.getDeliveryFee() != 500 && requestDto.getDeliveryFee() != 0) {
                throw new IllegalArgumentException("500원 단위로만 입력이 가능합니다.");
            }
        //1000원 이상일때는 500원 단위로만 가능
        }else {
            if (requestDto.getDeliveryFee()%1000 != 500 && requestDto.getDeliveryFee()%1000 != 0) {
                throw new IllegalArgumentException("500원 단위로만 입력이 가능합니다.");
            }
        }
        Restaurant restaurant = new Restaurant(requestDto);

        return restaurant;
    }
}
