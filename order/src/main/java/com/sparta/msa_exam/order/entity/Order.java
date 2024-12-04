package com.sparta.msa_exam.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> productIds = new ArrayList<>();

    public void setItemList(List<OrderItem> orderItemList) {
        this.productIds = orderItemList;
    }

    public void addItem(Long productId) {
        this.productIds.add(new OrderItem(this, productId));
    }

}
