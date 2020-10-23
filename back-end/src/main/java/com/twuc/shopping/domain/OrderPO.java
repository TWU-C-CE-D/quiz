package com.twuc.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "ShopOrder")
public class OrderPO {

    @Id
    private String id;

    private int total;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderPO", fetch=FetchType.LAZY, orphanRemoval = true)
    private List<OrderItemPO> orderItem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderItemPO> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemPO> orderItem) {
        this.orderItem = orderItem;
    }
}
