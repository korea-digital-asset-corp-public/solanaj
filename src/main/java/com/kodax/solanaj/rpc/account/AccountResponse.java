package com.kodax.solanaj.rpc.account;

/*
 * Copyright (c) 2024 Korea Digital Asset
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of $Korea Digital Asset.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered
 * into with Korea Digital Asset.
 */

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

public class AccountResponse {

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class GetAccountInfo {
    private Context context;
    private Value value;
  }

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Context {
    private long slot;
  }

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Value {
    private String[] data;
    private boolean executable;
    private BigInteger lamports;
    private String owner;
    private BigInteger rentEpoch;
    private BigInteger space;
  }
}
