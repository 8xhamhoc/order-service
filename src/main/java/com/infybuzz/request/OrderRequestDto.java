package com.infybuzz.request;

import lombok.Data;

@Data
public class OrderRequestDto {

    private Integer userId;
    private Integer productId;
    private Long orderId;

}