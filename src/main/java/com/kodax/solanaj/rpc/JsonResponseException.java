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

import lombok.Getter;

@Getter
public class JsonResponseException extends RuntimeException {
  private final JsonRpcResponse<?> response;

  public JsonResponseException(String message, JsonRpcResponse<?> response) {
    super(message);
    this.response = response;
  }
}
