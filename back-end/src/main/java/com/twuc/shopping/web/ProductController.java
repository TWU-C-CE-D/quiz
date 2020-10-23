package com.twuc.shopping.web;

import com.twuc.shopping.domain.Product;
import com.twuc.shopping.model.addProduct.AddProductRequest;
import com.twuc.shopping.model.getProducts.GetProductsResponse;
import com.twuc.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wudibin
 * 2020/10/23
 */
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody @Valid AddProductRequest request) {
        productService.save(request);
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<GetProductsResponse> getProducts() {
        List<Product> products = productService.findAll();
        return products.stream().map(p -> GetProductsResponse.builder()
                .name(p.getName())
                .price(p.getPrice())
                .unit(p.getUnit())
                .url(p.getUrl())
                .build()).collect(Collectors.toList());
    }

}
