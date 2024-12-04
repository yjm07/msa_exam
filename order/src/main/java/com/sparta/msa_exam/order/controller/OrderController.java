package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.service.OrderService;
import com.sparta.msa_exam.order.dto.OrderReqDto;
import com.sparta.msa_exam.order.dto.OrderResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResDto createOrder(@RequestBody OrderReqDto orderReqDto, @RequestParam(required = false) String fail) {
        return orderService.createOrder(orderReqDto, fail);
    }

    @PutMapping("{orderId}")
    public OrderResDto addItem(@PathVariable Long orderId, @RequestBody Long productId) {
        return orderService.addItem(orderId, productId);
    }

    @GetMapping("{orderId}")
    public OrderResDto getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }
}
