package com.kodax.solanaj.rpc;

import lombok.Getter;

@Getter
public class JsonResponseException extends RuntimeException {
    private final JsonRpcResponse<?> response;

    public JsonResponseException(String message, JsonRpcResponse<?> response) {
        super(message);
        this.response = response;
    }
}
