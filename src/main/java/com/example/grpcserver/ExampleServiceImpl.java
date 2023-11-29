package com.example.grpcserver;

import com.example.grpcdemo.ExampleProtos.ExampleRequest;
import com.example.grpcdemo.ExampleProtos.ExampleResponse;
import com.example.grpcdemo.ExampleServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class ExampleServiceImpl extends ExampleServiceGrpc.ExampleServiceImplBase {

  private final RandomService randomService;

  public ExampleServiceImpl(RandomService randomService) {
    this.randomService = randomService;
  }


  @Override
  public void exampleMethod(
          ExampleRequest request, StreamObserver<ExampleResponse> responseObserver) {
    log.info("Yay grpc request received with {}", request);
    ExampleResponse exampleResponse =
            ExampleResponse.newBuilder().setResponse(randomService.magic(request.getMessage())).build();

    responseObserver.onNext(exampleResponse);
    responseObserver.onCompleted();
  }
}
