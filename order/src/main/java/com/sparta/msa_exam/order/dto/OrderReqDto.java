package com.sparta.msa_exam.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderReqDto {
    private List<Long> productIds;
}
