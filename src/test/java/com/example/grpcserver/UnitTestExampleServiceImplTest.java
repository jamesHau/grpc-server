package com.example.grpcserver;

import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.grpcdemo.ExampleProtos.ExampleRequest;
import static com.example.grpcdemo.ExampleProtos.ExampleResponse;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UnitTestExampleServiceImplTest {

  @Mock
  RandomService randomService;

  @InjectMocks
  ExampleServiceImpl exampleService;

  @Test
  public void testExampleMethod() throws Exception{
    when(randomService.magic(anyString())).thenReturn("mocked!");
    ExampleRequest exampleRequest =
            ExampleRequest.newBuilder().setMessage("any message").build();

    StreamRecorder<ExampleResponse> responseObserver = StreamRecorder.create();
    exampleService.exampleMethod(exampleRequest, responseObserver);
    if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
      fail("The call did not terminate in time");
    }
    assertNull(responseObserver.getError());
    List<ExampleResponse> results = responseObserver.getValues();
    assertEquals(1, results.size());
    ExampleResponse exampleResponse = results.get(0);
    assertEquals(ExampleResponse.newBuilder()
            .setResponse("mocked!")
            .build(), exampleResponse);
  }
}
