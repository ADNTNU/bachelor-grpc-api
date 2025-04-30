package no.ntnu.gr10.bachelor_grpc_api;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import no.ntnu.gr10.bachelor_grpc_api.proto.HelloReply;
import no.ntnu.gr10.bachelor_grpc_api.proto.HelloRequest;
import no.ntnu.gr10.bachelor_grpc_api.proto.SimpleGrpc;
import no.ntnu.gr10.bachelor_grpc_api.security.RolesAllowed;
import no.ntnu.gr10.bachelor_grpc_api.security.SecurityConstants;

@GrpcService
public class GrpcServerService extends SimpleGrpc.SimpleImplBase{

  @Override
  @RolesAllowed({"key2"})
  public void sayHello(HelloRequest req, StreamObserver<HelloReply> resp) {
    String greeting = "Hello, " + req.getName() + "! " + SecurityConstants.COMPANY_ID_CTX_KEY.get();
    resp.onNext(HelloReply.newBuilder().setMessage(greeting).build());
    resp.onCompleted();
  }

}
