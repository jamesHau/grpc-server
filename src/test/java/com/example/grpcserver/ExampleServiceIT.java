package com.example.grpcserver;

import com.example.grpcdemo.ExampleProtos;
import com.example.grpcdemo.ExampleServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        "grpc.server.inProcessName=test", // Enable inProcess server
        "grpc.server.port=-1", // Disable external server
        "grpc.client.inProcess.address=in-process:test" // Configure the client to connect to the inProcess server
})
@DirtiesContext // Ensures that the grpc-server is properly shutdown after each test
// Avoids "port already in use" during tests
public class ExampleServiceIT {

  @GrpcClient("inProcess")
  private ExampleServiceGrpc.ExampleServiceBlockingStub exampleService;

  @Test
  public void testExampleMethod() {
    ExampleProtos.ExampleRequest exampleRequest = ExampleProtos.ExampleRequest.newBuilder().setMessage("Hello it").build();

    ExampleProtos.ExampleResponse exampleResponse = exampleService.exampleMethod(exampleRequest);
    assertEquals(ExampleProtos.ExampleResponse.newBuilder()
            .setResponse("Did you just say Hello it")
            .build(), exampleResponse);
  }
}
