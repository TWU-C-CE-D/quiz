package com.twuc.shopping.repository;

import com.twuc.shopping.domain.OrderPO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by wudibin
 * 2020/10/23
 */
public interface OrderRepository extends CrudRepository<OrderPO, String> {

    @Override
    List<OrderPO> findAll();

}
