package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductReqDto;
import com.sparta.msa_exam.product.dto.ProductResDto;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResDto createProduct(ProductReqDto productReqDto) {
        Product product = new Product(productReqDto.getName(), productReqDto.getPrice());
        productRepository.save(product);
        return new ProductResDto(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResDto> getProducts() {
        return productRepository.findAll().stream().map(ProductResDto::new).toList();
    }

    @Transactional(readOnly = true)
    public String getProductName(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        return product.getName();
    }
}
