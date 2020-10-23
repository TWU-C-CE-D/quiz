package com.twuc.shopping.service;

import com.twuc.shopping.common.errors.ErrorCode;
import com.twuc.shopping.common.exceptions.BadRequestException;
import com.twuc.shopping.domain.Order;
import com.twuc.shopping.domain.OrderItem;
import com.twuc.shopping.domain.Product;
import com.twuc.shopping.model.addOrder.AddProductVO;
import com.twuc.shopping.repository.OrderRepository;
import com.twuc.shopping.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by wudibin
 * 2020/10/23
 */
public class OrderService {

    final OrderRepository orderRepository;

    final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void save(List<AddProductVO> addProductVOS) {
        AtomicInteger total = new AtomicInteger();
        List<OrderItem> orderItems = addProductVOS.stream().map(p -> {
            Optional<Product> optional = productRepository.findById(p.getName());
            if (!optional.isPresent()) {
                throw new BadRequestException(ErrorCode.PRODUCT_NOT_EXIST);
            }
            Product product = optional.get();
            total.addAndGet(product.getPrice());
            return OrderItem.builder()
                    .number(p.getNumber())
                    .product(product)
                    .build();
        }).collect(Collectors.toList());

        Order order = Order.builder()
                .orderItem(orderItems)
                .total(total.get())
                .build();
        orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }

    public void deleteById(String id) {
        orderRepository.deleteById(id);
    }
}
