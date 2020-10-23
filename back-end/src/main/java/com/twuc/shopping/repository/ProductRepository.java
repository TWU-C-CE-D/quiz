package com.twuc.shopping.repository;

import com.twuc.shopping.domain.ProductPO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by wudibin
 * 2020/10/23
 */
public interface ProductRepository extends CrudRepository<ProductPO, String> {

    @Override
    List<ProductPO> findAll();
}
