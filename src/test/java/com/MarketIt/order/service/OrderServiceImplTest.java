package com.MarketIt.order.service;

import com.MarketIt.order.order.dto.OrderRequestDTO;
import com.MarketIt.order.order.dto.OrderResponseDTO;
import com.MarketIt.order.order.entity.Order;
import com.MarketIt.order.order.repository.OrderRepository;
import com.MarketIt.order.order.service.OrderService;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    private Long orderIdx = 0L;
    @BeforeEach
    void init(){
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @AfterEach
    void delete(){
        if(!orderIdx.equals(0L))
        orderRepository.deleteById(orderIdx);
    }
    @Test
    public void create(){
        OrderRequestDTO req = OrderRequestDTO.builder()
            .itemName("테스트 아이템")
            .address("테스트 주소")
            .userIdx(1L)
            .build();

        Optional<Order> order = transactionTemplate.execute(status -> {
            OrderResponseDTO res = orderService.createOrder(req);
            orderIdx = res.getOrderIdx();

            Optional<Order> findOrder = orderRepository.findById(orderIdx);

            return findOrder;
        });

        if(order.isEmpty()){
            return;
        }
        Order res = order.get();

        Assertions.assertThat(req.getItemName()).isEqualTo(res.getItemName());
        Assertions.assertThat(req.getAddress()).isEqualTo(res.getAddress());
        Assertions.assertThat(req.getUserIdx()).isEqualTo(res.getUserIdx());

    }
    @Test
    public void complete(){
        Order test = Order.builder()
            .itemName("테스트 아이템")
            .address("테스트 주소")
            .userIdx(1L)
            .build();

        Optional<Order> orderRes = transactionTemplate.execute(status -> {
            Order res = orderRepository.save(test);
            orderIdx = res.getOrderIdx();

            orderService.completeOrder(2L, orderIdx);

            Optional<Order> findOrder = orderRepository.findById(orderIdx);

            return findOrder;
        });
        if(orderRes.isEmpty()){
            return;
        }

        Order order = orderRes.get();

        Assertions.assertThat(order.getCompleteUser()).isEqualTo(2L);
        Assertions.assertThat(order.getStatus()).isEqualTo("C");

    }

    @Test
    public void getOrder(){
        Order test = Order.builder()
            .itemName("테스트 아이템")
            .address("테스트 주소")
            .userIdx(1L)
            .build();
        Optional<Order> orderRes = transactionTemplate.execute(status -> {
            Order res = orderRepository.save(test);
            orderIdx = res.getOrderIdx();

            Optional<Order> findOrder = orderRepository.findById(orderIdx);

            return findOrder;
        });
        if(orderRes.isEmpty()) {
            return;

        }
        Order order = orderRes.get();

        Assertions.assertThat(test.getItemName()).isEqualTo(order.getItemName());
        Assertions.assertThat(test.getAddress()).isEqualTo(order.getAddress());
        Assertions.assertThat(test.getUserIdx()).isEqualTo(order.getUserIdx());
    }

    @Test
    public void getOrderList(){
        Order req1 = Order.builder()
            .itemName("item1")
            .address("address1")
            .userIdx(1L)
            .build();
        Order req2 = Order.builder()
            .itemName("item2")
            .address("address2")
            .userIdx(1L)
            .build();
        Order req3 = Order.builder()
            .itemName("item3")
            .address("address3")
            .userIdx(1L)
            .build();

        int size = 3;

        List<Order> orderList = Arrays.asList(req1,req2,req3);
        Page<OrderResponseDTO> res= transactionTemplate.execute(status -> {
            List<Order> saveOrderList = orderRepository.saveAll(orderList);
            Page<OrderResponseDTO> responseDTO = orderService.getOrderList(0,size);
            List<Long> orderIdxList = saveOrderList.stream().map(Order :: getOrderIdx).collect(
                Collectors.toList());

            orderRepository.deleteAllById(orderIdxList);

            return responseDTO;
        });

        Assertions.assertThat(size).isEqualTo(res.getSize());
    }

}
