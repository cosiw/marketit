package com.MarketIt.order.order.dto;

import com.MarketIt.order.order.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDTO {
    private Long orderIdx;
    private String itemName;
    private Long userIdx;
    private String address;
    private String status;
    private Long completeUser;

    @Builder
    public OrderResponseDTO(Order Entity){
        this.orderIdx = Entity.getOrderIdx();
        this.itemName = Entity.getItemName();
        this.userIdx = Entity.getUserIdx();
        this.address = Entity.getAddress();
        this.status = Entity.getStatus();
        this.completeUser = Entity.getCompleteUser();
    }
}
