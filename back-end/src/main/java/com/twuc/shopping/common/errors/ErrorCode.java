package com.twuc.shopping.common.errors;

import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by wudibin
 * 2020/10/23
 */
public enum ErrorCode {

    INVALID_REQUEST(10000, "请求参数错误"),
    PRODUCT_NAME_EXIST(10001, "商品名称已存在"),
    SHOPPING_CART_EMPTY(10002, "购物车为空"),
    PRODUCT_NOT_EXIST(10003, "商品不存在"),
    ORDER_NOT_EXIST(10004, "订单不存在");

    private static final Set<String> ERROR_CODE_VALUES = ImmutableSet.copyOf(
            Arrays.stream(values()).map(Enum::toString).collect(Collectors.toSet())
    );

    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static boolean contains(String code) {
        return ERROR_CODE_VALUES.contains(code);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @JsonValue
    public ErrorMessage toErrorMessage() {
        return new ErrorMessage(code, message, new HashMap<>());
    }

}
