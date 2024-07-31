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

import java.io.IOException;
import java.net.URI;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RpcClient implements AutoCloseable {
  private final CloseableHttpClient httpClient;
  private final String endpoint;
  private final ObjectMapper objectMapper;

  public RpcClient(String endpoint) {
    this.httpClient = HttpClients.createDefault();
    this.endpoint = endpoint;
    this.objectMapper = new ObjectMapper();
  }

  public <T> JsonRpcResponse<T> request(JsonRpcRequest request, Class<T> responseType) {
    HttpPost post = new HttpPost(URI.create(endpoint));
    try {
      post.setEntity(new StringEntity(objectMapper.writeValueAsString(request)));
    } catch (IOException e) {
      throw new RpcClientException("Failed to serialize the request", e);
    }
    post.setHeader("Content-Type", "application/json");

    try (CloseableHttpResponse response = httpClient.execute(post)) {
      int statusCode = response.getCode();
      if (statusCode < 200 || statusCode >= 300) {
        String responseBody = EntityUtils.toString(response.getEntity());
        throw new HttpException(
            statusCode, responseBody, "Failed : HTTP error code : " + statusCode);
      }

      String responseBody = EntityUtils.toString(response.getEntity());
      JsonRpcResponse<T> rpcResponse =
          objectMapper.readValue(
              responseBody,
              objectMapper
                  .getTypeFactory()
                  .constructParametricType(JsonRpcResponse.class, responseType));

      if (rpcResponse.getError() != null) {
        throw new JsonResponseException("RPC Error: " + responseBody, rpcResponse);
      }

      return rpcResponse;
    } catch (ParseException e) {
      throw new RpcClientException("Failed to parse the response: " + e.getMessage(), e);
    } catch (IOException e) {
      throw new RpcClientException("HTTP request failed: " + e.getMessage(), e);
    }
  }

  @Override
  public void close() throws IOException {
    httpClient.close();
  }
}
