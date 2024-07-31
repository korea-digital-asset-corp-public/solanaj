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

import com.kodax.solanaj.rpc.JsonRpcRequest;
import com.kodax.solanaj.rpc.Requester;
import com.kodax.solanaj.rpc.RpcClient;
import com.kodax.solanaj.rpc.account.requester.GetAccountInfoRequester;

public class AccountRpcClient extends RpcClient {

    public AccountRpcClient(String endpoint) {
        super(endpoint);
    }

    public Requester<AccountResponse.GetAccountInfo> getAccountInfo(String accountAddress) {
        JsonRpcRequest request = JsonRpcRequest.create("getAccountInfo", new Object[]{accountAddress, new Encoding(determineEncoding(accountAddress))});
        return GetAccountInfoRequester.of(this, request);
    }

    private String determineEncoding(String accountAddress) {
        if (accountAddress.matches("[a-zA-Z0-9]{32}")) {
            return "base58";
        } else if (accountAddress.matches("[A-Za-z0-9+/=]+")) {
            return "base64";
        } else {
            throw new IllegalArgumentException("Unsupported encoding type for account address: " + accountAddress);
        }
    }

    public static class Encoding {
        private final String encoding;

        public Encoding(String encoding) {
            this.encoding = encoding;
        }

        public String getEncoding() {
            return encoding;
        }
    }
}
