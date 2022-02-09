package com.h99.week03work.dtoOrder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderCheckDto {
    private Long restaurantId;
    private Long foodId;
    private int quantity;
}
