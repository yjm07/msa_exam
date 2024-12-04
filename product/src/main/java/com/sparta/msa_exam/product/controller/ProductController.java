package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.service.ProductService;
import com.sparta.msa_exam.product.dto.ProductReqDto;
import com.sparta.msa_exam.product.dto.ProductResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("products")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResDto createProduct(@RequestBody ProductReqDto productReqDto) {
        return productService.createProduct(productReqDto);
    }

    @GetMapping
    public List<ProductResDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("{productId}")
    public String getProductName(@PathVariable Long productId) {
        return productService.getProductName(productId);
    }
}