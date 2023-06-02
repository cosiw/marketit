package com.MarketIt.order.order.controller;

import com.MarketIt.order.order.dto.OrderRequestDTO;
import com.MarketIt.order.order.dto.OrderResponseDTO;
import com.MarketIt.order.order.service.OrderService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }
    @PostMapping(value="")
    public ResponseEntity createOrder(@RequestBody OrderRequestDTO req){
        OrderResponseDTO res = orderService.createOrder(req);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @PatchMapping(value="{orderIdx}")
    public ResponseEntity completeOrder(@PathVariable Long orderIdx, @RequestParam Long completeUser){
        OrderResponseDTO res = orderService.completeOrder(completeUser, orderIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping(value="{orderIdx}")
    public ResponseEntity getOrder(@PathVariable Long orderIdx){
        OrderResponseDTO res = orderService.getOrder(orderIdx);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping(value="")
    public ResponseEntity<Page<OrderResponseDTO>> getOrderList(@RequestParam(value="page") int page, @RequestParam(value="size") int size){
        Page<OrderResponseDTO> res = orderService.getOrderList(page, size);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
