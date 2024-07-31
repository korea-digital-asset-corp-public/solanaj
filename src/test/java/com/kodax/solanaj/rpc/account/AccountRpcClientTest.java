package com.kodax.solanaj.rpc.account;

import com.kodax.solanaj.rpc.Requester;
import com.kodax.solanaj.rpc.RpcEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountRpcClientTest {
    private AccountRpcClient accountRpcClient;
    private String accountAddress = "53iDT7fiG4KKshzDU7mvmC1YnfsXao8Mtyp8McGRG77C";

    @BeforeEach
    void setUp() {
        this.accountRpcClient = new AccountRpcClient(RpcEndpoint.Devnet);
    }

    @Test
    void getAccountInfo() {
        Requester<AccountResponse.GetAccountInfo> requester = this.accountRpcClient.getAccountInfo(this.accountAddress);

        AccountResponse.GetAccountInfo response = requester.execute();

        assertNotNull(response);
        assertNotNull(response.getContext());
        assertNotNull(response.getValue());
    }

    @Test
    void getAccountInfoAsync() throws Exception {
        Requester<AccountResponse.GetAccountInfo> requester = this.accountRpcClient.getAccountInfo(this.accountAddress);

        AccountResponse.GetAccountInfo response = requester.executeAsync().get();

        assertNotNull(response);
        assertNotNull(response.getContext());
        assertNotNull(response.getValue());

        System.out.println("Lamports: " + response.getValue().getLamports());
    }
}
