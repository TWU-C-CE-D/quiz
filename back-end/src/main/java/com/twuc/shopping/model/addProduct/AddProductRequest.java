package com.twuc.shopping.model.addProduct;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by wudibin
 * 2020/10/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {

    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private int price;

    @NotNull
    private String unit;

    @NotNull
    private String url;

}
