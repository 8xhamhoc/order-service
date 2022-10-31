package com.infybuzz.feignclients;

import com.infybuzz.request.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-feign-client", url = "http://localhost:8083", path = "/api/inventories")
public interface InventoryFeignClient {

    @PostMapping
    void update(@RequestBody InventoryDto inventoryDto);
}
