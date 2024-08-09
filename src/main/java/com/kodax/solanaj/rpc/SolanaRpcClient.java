package com.kodax.solanaj.rpc;

/*
 * Copyright (c) 2024 Korea Digital Asset
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Korea Digital Asset.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered
 * into with Korea Digital Asset.
 */

import com.kodax.solanaj.rpc.account.AccountRpcClient;

public class SolanaRpcClient {
  public final AccountRpcClient account;

  public SolanaRpcClient(String endpoint) {
    this.account = new AccountRpcClient(endpoint);
  }
}
