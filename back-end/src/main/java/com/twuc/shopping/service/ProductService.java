package com.twuc.shopping.service;

import com.twuc.shopping.common.errors.ErrorCode;
import com.twuc.shopping.common.exceptions.BadRequestException;
import com.twuc.shopping.domain.ProductPO;
import com.twuc.shopping.model.addProduct.AddProductRequest;
import com.twuc.shopping.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by wudibin
 * 2020/10/23
 */
public class ProductService {

    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void save(AddProductRequest request) {
        Optional<ProductPO> optional = productRepository.findById(request.getName());
        if (optional.isPresent()) {
            throw new BadRequestException(ErrorCode.PRODUCT_NAME_EXIST);
        }
        ProductPO productPO = ProductPO.builder()
                .name(request.getName())
                .price(request.getPrice())
                .unit(request.getUnit())
                .url(request.getUrl())
                .build();
        productRepository.save(productPO);
    }

    public List<ProductPO> findAll() {
        return productRepository.findAll();
    }

}
