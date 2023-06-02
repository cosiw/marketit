package com.MarketIt.order.order.dto;

import com.MarketIt.order.order.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDTO {
    private String itemName;
    private String address;
    private Long userIdx;

    @Builder
    public OrderRequestDTO(String itemName, String address, Long userIdx){
        this.itemName = itemName;
        this.address = address;
        this.userIdx = userIdx;
    }
    public Order dtoToEntity(){
        return Order.builder()
            .itemName(itemName)
            .address(address)
            .userIdx(userIdx)
            .build();
    }
}
