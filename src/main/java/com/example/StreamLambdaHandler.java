package com.example;


import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.exceptions.LambdaException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamLambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {

  private static final SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

  static {
    try {
      log.debug("Initializing Lambda handler");
      handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
    } catch (ContainerInitializationException e) {
      // if we fail here. We re-throw the exception to force another cold start
      log.error("Failed to initialize Lambda handler", e);
      throw new LambdaException("Could not initialize Spring Boot application", e);
    }
  }

  @Override
  public AwsProxyResponse handleRequest(AwsProxyRequest awsProxyRequest, Context context) {
    return handler.proxy(awsProxyRequest, context);
  }
}