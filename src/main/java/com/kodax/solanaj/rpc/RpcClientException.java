package com.kodax.solanaj.rpc;

import lombok.Getter;

@Getter
public class RpcClientException extends RuntimeException {
    public RpcClientException(String message, Throwable cause) {
        super(message, cause);
    }
}