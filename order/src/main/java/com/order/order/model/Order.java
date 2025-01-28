package com.order.order.model;

import lombok.Data;

@Data
public class Order {

    private String orderId;
    private String productId;
    private String productName;
    private String productPrice;
    private String productQuantity;


}
