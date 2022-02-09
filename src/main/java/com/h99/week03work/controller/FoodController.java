package com.h99.week03work.controller;

import com.h99.week03work.dto.FoodRequestDto;
import com.h99.week03work.model.Food;
import com.h99.week03work.repository.FoodRepository;
import com.h99.week03work.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class FoodController {

    private final FoodService foodService;
    private final FoodRepository foodRepository;

    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void creatFood(@PathVariable Long restaurantId, @RequestBody List<Map<String, Object>> list){
        for(int i = 0; i < list.size(); i++){
            for(int j = i + 1; j < list.size(); j++){
                if(list.get(i).get("name").toString().equals(list.get(j).get("name").toString())){
                    throw new IllegalArgumentException("등록하려는 음식 중에 중복된 음식 이름이 있습니다.");
                }
            }
        }
        for(Map<String, Object> lists : list){
            System.out.println(list);
            System.out.println(lists);
            FoodRequestDto requestDto = new FoodRequestDto();
            requestDto.setName(lists.get("name").toString());
            requestDto.setPrice((Integer) lists.get("price"));
            Food food = foodService.registerFood(requestDto, restaurantId);
//            System.out.println(food);
            foodRepository.save(food);
        }

//        Food food = foodService.registerFood(requestDto, restaurantId);
//        foodRepository.save(food);
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> getAllFood(@PathVariable Long restaurantId){
        return foodRepository.findAllByRestaurantId(restaurantId);
    }
}
