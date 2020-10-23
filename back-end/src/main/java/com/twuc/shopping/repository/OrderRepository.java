package com.twuc.shopping.repository;

import com.twuc.shopping.domain.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by wudibin
 * 2020/10/23
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {
}
