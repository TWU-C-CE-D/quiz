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
@Data
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderPO", fetch=FetchType.EAGER)
    private List<OrderItemPO> orderItem;

}
