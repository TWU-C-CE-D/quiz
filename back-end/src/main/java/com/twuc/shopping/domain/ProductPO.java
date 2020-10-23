package com.twuc.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "ShopProduct")
public class ProductPO {

    @Id
    private String name;

    private int price;

    private String unit;

    private String url;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productPO", fetch=FetchType.LAZY)
    private List<OrderItemPO> orderItem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<OrderItemPO> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemPO> orderItem) {
        this.orderItem = orderItem;
    }
}
