package com.twuc.shopping.web;

import com.twuc.shopping.common.errors.ErrorCode;
import com.twuc.shopping.common.exceptions.BadRequestException;
import com.twuc.shopping.model.addOrder.AddOrderRequest;
import com.twuc.shopping.model.addOrder.ProductVO;
import com.twuc.shopping.service.OrderService;
import com.twuc.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        if (request.getProductVOs().isEmpty()) {
            throw new BadRequestException(ErrorCode.SHOPPING_CART_EMPTY);
        }
        orderService.save(request.getProductVOs());
    }
}
