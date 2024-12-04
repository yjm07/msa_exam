package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.Order;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResDto {
    private Long orderId;
    private List<OrderItemResDto> orderItems;

    public OrderResDto(Order order) {
        this.orderId = order.getOrderId();
        this.orderItems = order.getProductIds().stream().map(OrderItemResDto::new).toList();
    }
}
