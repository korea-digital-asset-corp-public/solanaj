package com.kodax.solanaj.rpc;

/*
 * Copyright (c) 2024 Korea Digital Asset
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of $Korea Digital Asset.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered
 * into with Korea Digital Asset.
 */

import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

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
    this.id = nextId.getAndIncrement();
    this.params = params;
  }
}
