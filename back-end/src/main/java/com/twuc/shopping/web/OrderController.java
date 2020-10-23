package com.twuc.shopping.web;

import com.twuc.shopping.common.errors.ErrorCode;
import com.twuc.shopping.common.exceptions.BadRequestException;
import com.twuc.shopping.domain.OrderPO;
import com.twuc.shopping.model.addOrder.AddOrderRequest;
import com.twuc.shopping.model.getOrders.GetOrderVO;
import com.twuc.shopping.model.getOrders.GetOrdersResponse;
import com.twuc.shopping.model.getOrders.GetProductVO;
import com.twuc.shopping.service.OrderService;
import com.twuc.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrder(@RequestBody @Valid AddOrderRequest request) {
        if (request.getAddProductVOs().isEmpty()) {
            throw new BadRequestException(ErrorCode.SHOPPING_CART_EMPTY);
        }
        orderService.save(request.getAddProductVOs());
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public GetOrdersResponse getOrders() {
        List<OrderPO> orderPOs = orderService.findAll();
        List<GetOrderVO> getOrderVOs = orderPOs.stream().map(o -> {
            AtomicInteger sortId = new AtomicInteger(1);
            List<GetProductVO> getProductVOs = o.getOrderItem().stream().map(i -> GetProductVO.builder()
                    .sortId(sortId.getAndIncrement())
                    .name(i.getProductPO().getName())
                    .price(i.getProductPO().getPrice())
                    .num(i.getNumber() + i.getProductPO().getUnit())
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

    @Transactional
    @DeleteMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable String id) {
        Optional<OrderPO> optional = orderService.findById(id);
        if (!optional.isPresent()) {
            throw new BadRequestException(ErrorCode.ORDER_NOT_EXIST);
        }
        orderService.deleteById(id);
    }
}
