package com.kodax.solanaj.rpc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class JsonRpcResponse<T> {
    @JsonProperty("jsonrpc")
    private String jsonrpc = "2.0";

    @JsonProperty("result")
    private T result;

    @JsonProperty("error")
    private RpcError error;

    @JsonProperty("id")
    private int id;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RpcError {
        @JsonProperty("code")
        private int code;
        @JsonProperty("message")
        private String message;
        @JsonProperty("data")
        private Object data;
    }
}