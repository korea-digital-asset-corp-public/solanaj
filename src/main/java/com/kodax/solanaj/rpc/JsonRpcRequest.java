package com.kodax.solanaj.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class JsonRpcRequest {
    private static AtomicLong nextId = new AtomicLong(0);

    @JsonProperty("jsonrpc")
    private final String jsonrpc = "2.0";

    @JsonProperty("method")
    private String method;

    @JsonProperty("id")
    private long id;

    @JsonProperty("params")
    private Object[] params;

    public static JsonRpcRequest create(String method, int id, Object[] params) {
        return new JsonRpcRequest(method, id, params);
    }

    public static JsonRpcRequest create(String method, Object[] params) {
        return new JsonRpcRequest(method, params);
    }

    private JsonRpcRequest(String method, int id, Object[] params) {
        this.method = method;
        this.id = id;
        this.params = params;
    }

    private JsonRpcRequest(String method, Object[] params) {
        this.method = method;
        this.id =  nextId.getAndIncrement();
        this.params = params;
    }
}
