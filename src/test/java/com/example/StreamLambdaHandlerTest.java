package com.example;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StreamLambdaHandlerTest {

  private static StreamLambdaHandler handler;
  private static Context lambdaContext;

  @BeforeAll
  static void setUp() {
    handler = new StreamLambdaHandler();
    lambdaContext = new MockLambdaContext();
  }

  @Test
  void testUserApi() {
    AwsProxyRequest request = new AwsProxyRequestBuilder("/v1/user", HttpMethod.GET).header(
        HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).build();

    AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
    assertNotNull(response);
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());

    assertFalse(response.isBase64Encoded());

    assertTrue(response.getBody().contains("id"));
    assertTrue(response.getBody().contains("1"));

    assertTrue(response.getMultiValueHeaders().containsKey(HttpHeaders.CONTENT_TYPE));
    assertTrue(response.getMultiValueHeaders().getFirst(HttpHeaders.CONTENT_TYPE)
        .startsWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testInvalidRequest() {
    AwsProxyRequest request = new AwsProxyRequestBuilder("/pong", HttpMethod.GET).header(
        HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).build();

    AwsProxyResponse response = handler.handleRequest(request, lambdaContext);
    assertNotNull(response);
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatusCode());
  }
}
