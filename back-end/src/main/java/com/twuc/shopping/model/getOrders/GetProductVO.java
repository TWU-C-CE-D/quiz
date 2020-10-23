package com.twuc.shopping.model.getOrders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wudibin
 * 2020/10/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductVO {

    private int sortId;

    private String name;

    private int price;

    private String num;

}
