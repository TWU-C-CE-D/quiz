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
@Table(name = "ShopProduct")
public class Product {

    @Id
    private String name;

    private int price;

    private String unit;

    private String url;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "product")
    private List<Order> orders;

}
