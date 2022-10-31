package com.infybuzz.service;

import com.infybuzz.entity.OrderStatus;
import com.infybuzz.entity.PurchaseOrder;
import com.infybuzz.feignclients.InventoryFeignClient;
import com.infybuzz.feignclients.PaymentFeignClient;
import com.infybuzz.repository.PurchaseOrderRepository;
import com.infybuzz.request.InventoryDto;
import com.infybuzz.request.OrderRequestDto;
import com.infybuzz.request.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class OrderCommandService {

    @Autowired
    private Map<Integer, Integer> productPriceMap;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private InventoryFeignClient inventoryFeignClient;

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDTO) {
        PurchaseOrder purchaseOrder = this.purchaseOrderRepository.save(this.dtoToEntity(orderRequestDTO));
        purchaseOrderRepository.save(purchaseOrder);

        try {
            inventoryFeignClient.update(InventoryDto.of(orderRequestDTO.getOrderId(), orderRequestDTO.getProductId()));
            paymentFeignClient.deduct(new PaymentDto(orderRequestDTO.getOrderId(), orderRequestDTO.getUserId(), purchaseOrder.getPrice()));
            purchaseOrder.setOrderStatus(OrderStatus.ORDER_COMPLETED);
        } catch (Exception e) {
            purchaseOrder.setOrderStatus(OrderStatus.ORDER_CANCELLED);
        }

        return purchaseOrder;
    }

    private PurchaseOrder dtoToEntity(final OrderRequestDto dto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(dto.getOrderId());
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(productPriceMap.get(purchaseOrder.getProductId()));
        return purchaseOrder;
    }

}
