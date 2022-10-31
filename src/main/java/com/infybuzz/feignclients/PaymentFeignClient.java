package com.infybuzz.feignclients;

import com.infybuzz.request.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-feign-client", url = "http://localhost:8082", path = "/api/payments")
public interface PaymentFeignClient {

    @PostMapping("/deduct")
    void deduct(@RequestBody PaymentDto paymentDto);

}
