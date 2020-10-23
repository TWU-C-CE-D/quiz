package com.twuc.shopping.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by wudibin
 * 2020/10/23
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ShopOrder")
public class Order {

    @Id
    @GeneratedValue
    private String id;

    private int total;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "order")
    private List<OrderItem> orderItem;

}
