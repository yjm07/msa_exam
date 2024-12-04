package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResDto {

    private Long productId;
    private String name;
    private Integer price;

    public ProductResDto(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.price = product.getSupplyPrice();
    }
}
