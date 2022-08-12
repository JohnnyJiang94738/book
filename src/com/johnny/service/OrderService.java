package com.johnny.service;

import com.johnny.pojo.Cart;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId);
}
