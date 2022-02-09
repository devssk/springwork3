package com.h99.week03work.service;

import com.h99.week03work.dto.FoodRequestDto;
import com.h99.week03work.model.Food;
import com.h99.week03work.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public Food registerFood(FoodRequestDto requestDto, Long restaurantId){
        //중복된 음식 이름 찾기
        Optional<Food> sameName = foodRepository.findByRestaurantIdAndName(restaurantId,requestDto.getName());
        if(sameName.isPresent()){
            throw new IllegalArgumentException("이미 등록된 음식 중에 중복된 음식 이름이 있습니다.");
        }
        //허용값: 100원 ~ 1,000,000원
        if(requestDto.getPrice() < 100 || requestDto.getPrice() > 1000000){
            throw new IllegalArgumentException("100원에서 1,000,000원 사이만 입력 가능합니다.");
        }
        //100원 단위 입력
        if(requestDto.getPrice()%100 != 0){
            throw new IllegalArgumentException("100원 단위로만 입력이 가능합니다.");
        }
        Food food = new Food(requestDto,restaurantId);
        return food;
    }
}
