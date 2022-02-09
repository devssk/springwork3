package com.h99.week03work.dtoOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FoodOrderDto {
    private String name;
    private int quantity;
    private int price;
}
