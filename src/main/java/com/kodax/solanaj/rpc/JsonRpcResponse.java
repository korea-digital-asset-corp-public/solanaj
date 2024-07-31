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
