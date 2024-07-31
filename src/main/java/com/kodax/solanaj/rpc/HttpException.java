package com.kodax.solanaj.rpc;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;

    public HttpException(int statusCode, String responseBody, String message) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }
}