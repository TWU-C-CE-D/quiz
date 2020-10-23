package com.twuc.shopping.model.product;

import lombok.*;

/**
 * Created by wudibin
 * 2020/10/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductsResponse {

    private String name;

    private int price;

    private String unit;

    private String url;

}
