package com.order.order.controller;

import com.order.order.model.Order;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @GetMapping("/getOrders")
    public String getOrders() {
        return "Orders";
    }

    @PostMapping("/placeOrder")
    public Order placeOrder(@RequestBody Order order){
        System.out.println("Order Placed: "+order);

        return order;

    }


}
