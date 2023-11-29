package com.example.grpcserver;

import com.example.grpcdemo.ExampleProtos;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class GrpcServerApplicationTests {

	@Autowired
	private ExampleServiceImpl exampleService;

	@Test
	void contextLoads() {
	}

	@Test
	void springBootTest() throws Exception {
		StreamRecorder<ExampleProtos.ExampleResponse> responseObserver = StreamRecorder.create();
		exampleService.exampleMethod(ExampleProtos.ExampleRequest.newBuilder().setMessage("test").build(), responseObserver);

		if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
			fail("The call did not terminate in time");
		}
		assertNull(responseObserver.getError());
		List<ExampleProtos.ExampleResponse> results = responseObserver.getValues();
		assertEquals(1, results.size());
		ExampleProtos.ExampleResponse exampleResponse = results.get(0);
		assertEquals(ExampleProtos.ExampleResponse.newBuilder()
						.setResponse("Did you just say test")
						.build(), exampleResponse);
	}
}
