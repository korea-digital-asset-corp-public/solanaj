package com.kodax.solanaj.rpc;

import com.kodax.solanaj.rpc.account.AccountRpcClient;

public class SolanaRpcClient {
    public final AccountRpcClient account;

    public SolanaRpcClient(String endpoint) {
        this.account = new AccountRpcClient(endpoint);
    }
}
