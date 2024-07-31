package com.kodax.solanaj.rpc;

import java.util.concurrent.CompletableFuture;

public interface Requester<T> {
    T execute();
    CompletableFuture<T> executeAsync();
}
