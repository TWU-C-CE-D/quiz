package com.twuc.shopping.repository;

import com.twuc.shopping.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wudibin
 * 2020/10/23
 */
public interface ProductRepository extends CrudRepository<Product, String> {
}
