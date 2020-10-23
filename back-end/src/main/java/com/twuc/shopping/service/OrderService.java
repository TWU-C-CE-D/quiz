package com.twuc.shopping.service;

import com.twuc.shopping.common.errors.ErrorCode;
import com.twuc.shopping.common.exceptions.BadRequestException;
import com.twuc.shopping.domain.OrderPO;
import com.twuc.shopping.domain.OrderItemPO;
import com.twuc.shopping.domain.ProductPO;
import com.twuc.shopping.model.addOrder.AddProductVO;
import com.twuc.shopping.repository.OrderItemRepository;
import com.twuc.shopping.repository.OrderRepository;
import com.twuc.shopping.repository.ProductRepository;

import java.util.Calendar;
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

    final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public void save(List<AddProductVO> addProductVOS) {
        AtomicInteger total = new AtomicInteger();
        AtomicInteger index = new AtomicInteger(1);
        OrderPO orderPO = OrderPO.builder().id(generateOrderNo()).build();
        List<OrderItemPO> orderItems = addProductVOS.stream().map(p -> {
            Optional<ProductPO> optional = productRepository.findById(p.getName());
            if (!optional.isPresent()) {
                throw new BadRequestException(ErrorCode.PRODUCT_NOT_EXIST);
            }
            ProductPO productPO = optional.get();
            total.addAndGet(productPO.getPrice());
            return OrderItemPO.builder()
                    .id(index.getAndIncrement())
                    .number(p.getNumber())
                    .orderPO(orderPO)
                    .productPO(productPO)
                    .build();
        }).collect(Collectors.toList());
        orderPO.setTotal(total.get());
        orderPO.setOrderItem(orderItems);
        orderRepository.save(orderPO);
    }

    private String generateOrderNo() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.getTime().getTime());
    }

    public List<OrderPO> findAll() {
        return orderRepository.findAll();
    }

    public Optional<OrderPO> findById(String id) {
        return orderRepository.findById(id);
    }

    public void deleteById(String id) {
        orderRepository.deleteById(id);
    }
}
