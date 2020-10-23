package com.twuc.shopping.model.addOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by wudibin
 * 2020/10/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductVO {

    @NotNull
    private String name;

    @NotNull
    private int number;

}
