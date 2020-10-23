package com.twuc.shopping.common.exceptions;

import com.twuc.shopping.common.errors.ErrorCode;
import lombok.Getter;

/**
 * Created by wudibin
 * 2020/10/23
 */
public class HttpException extends RuntimeException {
    @Getter
    private ErrorCode errorCode;

    public HttpException(ErrorCode code, String message) {
        super(message);
        this.errorCode = code;
    }

    public HttpException(ErrorCode code, Throwable cause) {
        super(code.getMessage(), cause);
        this.errorCode = code;
    }

    public HttpException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
    }

    public HttpException(ErrorCode code) {
        this(code, code.getMessage());
    }
}
