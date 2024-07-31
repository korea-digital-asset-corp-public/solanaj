package com.kodax.solanaj.rpc.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.math.BigInteger;

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
