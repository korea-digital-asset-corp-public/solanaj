package com.kodax.solanaj.rpc.account.requester;

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
import com.kodax.solanaj.rpc.account.AccountResponse;

import java.util.concurrent.CompletableFuture;

public class GetAccountInfoRequester implements Requester<AccountResponse.GetAccountInfo> {
    private final RpcClient rpcClient;
    private final JsonRpcRequest request;

    public static GetAccountInfoRequester of(RpcClient rpcClient, JsonRpcRequest request) {
        return new GetAccountInfoRequester(rpcClient, request);
    }

    private GetAccountInfoRequester(RpcClient rpcClient, JsonRpcRequest request) {
        this.rpcClient = rpcClient;
        this.request = request;
    }

    @Override
    public AccountResponse.GetAccountInfo execute() {
        return rpcClient.request(request, AccountResponse.GetAccountInfo.class).getResult();
    }

    @Override
    public CompletableFuture<AccountResponse.GetAccountInfo> executeAsync() {
        return CompletableFuture.supplyAsync(() -> rpcClient.request(request, AccountResponse.GetAccountInfo.class).getResult());
    }
}
