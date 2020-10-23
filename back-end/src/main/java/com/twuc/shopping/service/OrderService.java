package com.twuc.shopping.service;

import com.twuc.shopping.repository.OrderRepository;

/**
 * Created by wudibin
 * 2020/10/23
 */
public class OrderService {

    final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

}
