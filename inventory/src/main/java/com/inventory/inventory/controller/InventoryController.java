package com.inventory.inventory.controller;

import com.inventory.inventory.model.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
@RequestMapping("inventory")
public class InventoryController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/getInventory")
    @CircuitBreaker(name = "order", fallbackMethod = "getOrderFallback")
    public String getInventory() {
        log.info("--------------------------------------------------getInventory method called--------------------------------------------------");
        String block = webClientBuilder.build().get().uri("http://api-gateway/product/getProducts")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("webclient call block response: " + block);

        Order product1 = Order.builder()
                .orderId("1")
                .productId("1")
                .productName("Product1")
                .productPrice("100")
                .productQuantity("10")
                .build();

        Order block1 = webClientBuilder.build()
                .post()
                .uri("http://api-gateway/order/placeOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product1)
                .retrieve()
                .bodyToMono(Order.class)
                .block();


        System.out.println("block1 call order response: " + block1);
        return "Inventory : " + block;
    }

    public String getOrderFallback(Exception e) {
        return "Order Service is down. Please try again later";
    }

    @GetMapping("/getInventory2")
    public String getInventory2(){
        return "Inventory2";
    }
}
