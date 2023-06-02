package com.MarketIt.order.order.service;

import com.MarketIt.order.order.dto.OrderRequestDTO;
import com.MarketIt.order.order.dto.OrderResponseDTO;
import org.springframework.data.domain.Page;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO req);
    OrderResponseDTO completeOrder(Long completeUser, Long orderIdx);
    OrderResponseDTO getOrder(Long orderIdx);
    Page<OrderResponseDTO> getOrderList(int page, int size);
}
