package com.MarketIt.order.order.dto;

import com.MarketIt.order.order.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRequestDTO {
    private String itemName;
    private String address;
    private Long userIdx;


    public Order dtoToEntity(){
        return Order.builder()
            .itemName(itemName)
            .address(address)
            .userIdx(userIdx)
            .build();
    }
}
