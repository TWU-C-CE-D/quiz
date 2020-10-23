package com.twuc.shopping.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String name;

    private int price;

    private int number;

    private String unit;

    @ManyToOne
    private Product product;

}
