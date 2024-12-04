package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItemResDto {
    private Long orderItemId;
    private Long productId;

    public OrderItemResDto(OrderItem orderItem) {
        this.orderItemId = orderItem.getId();
        this.productId = orderItem.getProductId();
    }
}
