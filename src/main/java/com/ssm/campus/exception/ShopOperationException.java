package com.ssm.campus.exception;

/*
店铺操作异常类
**/
public class ShopOperationException extends RuntimeException {
    private static final long serivalVersionUID = 4771094100507194189L;

    public ShopOperationException(String message) {
        super(message);
    }
}
