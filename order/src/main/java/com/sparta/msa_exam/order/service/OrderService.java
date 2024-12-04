package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.dto.OrderReqDto;
import com.sparta.msa_exam.order.dto.OrderResDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderItem;
import com.sparta.msa_exam.order.repository.OrderItemRepository;
import com.sparta.msa_exam.order.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductClient productClient;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    public void registerEventListener() {
        circuitBreakerRegistry.circuitBreaker("productService").getEventPublisher()
                .onStateTransition(event -> log.info("CircuitBreaker State Transition: {}", event)) // 상태 전환 이벤트 리스너
                .onFailureRateExceeded(event -> log.info("CircuitBreaker Failure Rate Exceeded: {}", event)) // 실패율 초과 이벤트 리스너
                .onCallNotPermitted(event -> log.info("CircuitBreaker Call Not Permitted: {}", event)) // 호출 차단 이벤트 리스너
                .onError(event -> log.info("CircuitBreaker Error: {}", event)); // 오류 발생 이벤트 리스너
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackCreateOrder")
    public OrderResDto createOrder(OrderReqDto orderReqDto, String fail) {
        if (fail != null) {
            throw new RuntimeException("Fail");
        }
        Order order = new Order();
        orderRepository.save(order);
        List<OrderItem> orderItemList = orderReqDto.getProductIds().stream()
                .map(id -> new OrderItem(order, id))
                .toList();
        orderItemRepository.saveAll(orderItemList);
        order.setItemList(orderItemList);
        return new OrderResDto(order);
    }

    public OrderResDto addItem(Long orderId, Long productId) {
        Order order = findOrderById(orderId);
        String productName = productClient.getProductName(productId);
        if (productName == null) {
            throw new NullPointerException();
        }
        order.addItem(productId);
        return new OrderResDto(order);
    }

    @Transactional(readOnly = true)
    @Cacheable (cacheNames = "itemCache", key = "args[0]")
    public OrderResDto getOrder(Long orderId) {
        Order order = findOrderById(orderId);
        return new OrderResDto(order);
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public OrderResDto fallbackCreateOrder(OrderReqDto orderReqDto, String fail, Throwable t) {
        log.error("####Fallback triggered for productIds: {} due to: {}", orderReqDto.getProductIds(), t.getMessage());
        return null;
    }
}