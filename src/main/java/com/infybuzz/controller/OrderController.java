package com.infybuzz.controller;

import com.infybuzz.entity.PurchaseOrder;
import com.infybuzz.request.OrderRequestDto;
import com.infybuzz.service.OrderCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderCommandService commandService;


    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDto requestDTO) {
        requestDTO.setOrderId(requestDTO.getOrderId());
        return this.commandService.createOrder(requestDTO);
    }

}
