package com.twuc.shopping.web;

import com.twuc.shopping.common.errors.ErrorCode;
import com.twuc.shopping.common.exceptions.BadRequestException;
import com.twuc.shopping.domain.Order;
import com.twuc.shopping.model.addOrder.AddOrderRequest;
import com.twuc.shopping.model.getOrders.GetOrderVO;
import com.twuc.shopping.model.getOrders.GetOrdersResponse;
import com.twuc.shopping.model.getOrders.GetProductVO;
import com.twuc.shopping.service.OrderService;
import com.twuc.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by wudibin
 * 2020/10/23
 */
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    public void addOrder(@RequestBody @Valid AddOrderRequest request) {
        if (request.getAddProductVOS().isEmpty()) {
            throw new BadRequestException(ErrorCode.SHOPPING_CART_EMPTY);
        }
        orderService.save(request.getAddProductVOS());
    }

    @GetMapping("/orders")
    public GetOrdersResponse getOrders() {
        List<Order> orders = orderService.findAll();
        List<GetOrderVO> getOrderVOs = orders.stream().map(o -> {
            AtomicInteger sortId = new AtomicInteger(1);
            List<GetProductVO> getProductVOs = o.getOrderItem().stream().map(i -> GetProductVO.builder()
                    .sortId(sortId.getAndIncrement())
                    .name(i.getProduct().getName())
                    .price(i.getProduct().getPrice())
                    .num(i.getNumber() + i.getProduct().getUnit())
                    .build()).collect(Collectors.toList());
            return GetOrderVO.builder()
                    .orderId(o.getId())
                    .getProductVOs(getProductVOs)
                    .total(o.getTotal())
                    .build();
        }).collect(Collectors.toList());

        return GetOrdersResponse.builder()
                .getOrderVOs(getOrderVOs)
                .build();
    }
}
