package no.ntnu.gr10.bachelor_grpc_api;

import io.grpc.stub.StreamObserver;
import no.ntnu.gr10.bachelor_grpc_api.proto.HelloReply;
import no.ntnu.gr10.bachelor_grpc_api.proto.HelloRequest;
import no.ntnu.gr10.bachelor_grpc_api.proto.SimpleGrpc;
import org.springframework.stereotype.Service;

@Service
public class GrpcServerService extends SimpleGrpc.SimpleImplBase{

  @Override
  public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
    String greeting = "Hello, " + req.getName() + "!";
    HelloReply reply = HelloReply.newBuilder()
            .setMessage(greeting)
            .build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

}
