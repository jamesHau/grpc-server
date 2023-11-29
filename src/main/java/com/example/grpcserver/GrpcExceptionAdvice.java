package com.example.grpcserver;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

// If you are using grpc-spring-boot-starter use @GrpcAdvice, don't implement ServerInterceptor
@GrpcAdvice
public class GrpcExceptionAdvice {

  // StatusException is checked
  @GrpcExceptionHandler(IllegalArgumentException.class)
  public StatusException handleStatusRuntimeException(IllegalArgumentException e) {
    // Note withCause is not transferred to client
    Status status = Status.NOT_FOUND.withDescription("Your description").withCause(e);
    Metadata metadata = new Metadata();
    metadata.put(Metadata.Key.of("MY_KEY", Metadata.ASCII_STRING_MARSHALLER), "MY_VALUE");
    return status.asException(metadata);
  }

  // StatusRuntimeException is not checked (used for blockingstub)
  @GrpcExceptionHandler
  public StatusRuntimeException handleException(Exception e) {
    Status status = Status.INTERNAL.withDescription("Your description").withCause(e);
    Metadata metadata = new Metadata();
    metadata.put(Metadata.Key.of("MY_KEY", Metadata.ASCII_STRING_MARSHALLER), "MY_VALUE");
    return status.asRuntimeException(metadata);
  }
}
