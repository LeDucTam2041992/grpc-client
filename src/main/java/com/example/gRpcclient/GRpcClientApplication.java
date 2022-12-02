package com.example.gRpcclient;

import com.grpc.order.v1.Order;
import com.grpc.order.v1.OrderRequest;
import com.grpc.order.v1.OrderResponse;
import com.grpc.order.v1.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.JsonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GRpcClientApplication {
	public static void main(String[] args) {
		SpringApplication.run(GRpcClientApplication.class, args);

		ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8080)
				.usePlaintext()
				.build();

		OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub
				= OrderServiceGrpc.newBlockingStub(managedChannel);

		OrderRequest orderRequest = OrderRequest.newBuilder()
				.setEmail("hello@word.com")
				.setProduct("no-name")
				.setAmount(3)
				.build();

		OrderResponse orderResponse = orderServiceBlockingStub.executeOrder(orderRequest);

		System.out.println("Received response: "+orderResponse.getInfo());

		managedChannel.shutdown();
	}

}
