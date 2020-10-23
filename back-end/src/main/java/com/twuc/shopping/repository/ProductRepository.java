package com.twuc.shopping.repository;

import com.twuc.shopping.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by wudibin
 * 2020/10/23
 */
public interface ProductRepository extends CrudRepository<Product, String> {

    @Override
    List<Product> findAll();
}
