package com.twuc.shopping.model.getOrders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by wudibin
 * 2020/10/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderVO {

    private String orderId;

    private List<GetProductVO> getProductVOs;

    private int total;

}
