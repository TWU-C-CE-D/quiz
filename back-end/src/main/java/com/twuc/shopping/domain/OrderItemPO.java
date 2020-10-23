package com.twuc.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by wudibin
 * 2020/10/23
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(callSuper = true, exclude = "orderPO")
@Table(name = "ShopOrderItem")
public class OrderItemPO {

    @Id
    private int id;

    private int number;

    @ManyToOne
    @JsonIgnore
    private OrderPO orderPO;

    @ManyToOne
    @JsonIgnore
    private ProductPO productPO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public OrderPO getOrderPO() {
        return orderPO;
    }

    public void setOrderPO(OrderPO orderPO) {
        this.orderPO = orderPO;
    }

    public ProductPO getProductPO() {
        return productPO;
    }

    public void setProductPO(ProductPO productPO) {
        this.productPO = productPO;
    }
}
