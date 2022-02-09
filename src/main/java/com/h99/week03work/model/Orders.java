package com.h99.week03work.model;

import com.h99.week03work.dtoOrder.OrderCheckDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Orders {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column
    private Long restaurantId;

    @Column
    private Long foodId;

    @Column
    private int quantity;

    public Orders(OrderCheckDto orderCheckDto){
        this.restaurantId = orderCheckDto.getRestaurantId();
        this.foodId = orderCheckDto.getFoodId();
        this.quantity = orderCheckDto.getQuantity();
    }

}
