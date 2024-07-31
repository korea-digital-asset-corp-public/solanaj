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
public class HttpException extends RuntimeException {
  private final int statusCode;
  private final String responseBody;

  public HttpException(int statusCode, String responseBody, String message) {
    super(message);
    this.statusCode = statusCode;
    this.responseBody = responseBody;
  }
}
