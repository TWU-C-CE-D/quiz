package com.twuc.shopping.model.addOrder;

import lombok.*;

import java.util.List;

/**
 * Created by wudibin
 * 2020/10/23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderRequest {

    @NonNull
    List<AddProductVO> addProductVOS;

}
