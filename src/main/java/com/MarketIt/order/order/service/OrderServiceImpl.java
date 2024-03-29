package com.MarketIt.order.order.service;

import com.MarketIt.order.common.Exception.CustomException;
import com.MarketIt.order.order.dto.OrderRequestDTO;
import com.MarketIt.order.order.dto.OrderResponseDTO;
import com.MarketIt.order.order.entity.Order;
import com.MarketIt.order.order.repository.OrderRepository;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository= orderRepository;
    }
    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO req) {
        Order order = req.dtoToEntity();
        Order saveOrder = orderRepository.save(order);

        OrderResponseDTO res = new OrderResponseDTO(saveOrder);

        return res;

    }

    @Override
    @Transactional
    public OrderResponseDTO completeOrder(Long completeUser, Long orderIdx) {
        Optional<Order> findOrder = orderRepository.findById(orderIdx);
        if(findOrder.isEmpty()){
           throw new CustomException("주문이 존재하지 않습니다.", HttpServletResponse.SC_BAD_REQUEST);
        }

        Order order = findOrder.get();
        if(order.getStatus().equals("C")){
            throw new CustomException("이미 완료 상태입니다.", HttpServletResponse.SC_BAD_REQUEST);
        }
        order.completeOrder(completeUser);

        OrderResponseDTO res = new OrderResponseDTO(order);

        return res;
    }


    @Override
    @Transactional
    public OrderResponseDTO getOrder(Long orderIdx) {
        Optional<Order> findOrder = orderRepository.findById(orderIdx);
        if(findOrder.isEmpty()){
            throw new CustomException("주문이 존재하지 않습니다.", HttpServletResponse.SC_BAD_REQUEST);
        }
        Order order = findOrder.get();

        OrderResponseDTO res = new OrderResponseDTO(order);

        return res;
    }

    @Override
    @Transactional
    public Page<OrderResponseDTO> getOrderList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Order> findList = orderRepository.findAll(pageable);

        if(findList.isEmpty()){
            throw new CustomException("주문이 존재하지 않습니다.", HttpServletResponse.SC_BAD_REQUEST);
        }

        Page<OrderResponseDTO> res = findList.map(o -> new OrderResponseDTO(o));

        return res;
    }
}
