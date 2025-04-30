package no.ntnu.gr10.bachelor_grpc_api.fisheryActivity;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import no.ntnu.gr10.bachelor_grpc_api.security.RolesAllowed;
import no.ntnu.gr10.bachelor_grpc_api.security.SecurityConstants;
import com.google.protobuf.Timestamp;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@GrpcService
public class FisheryActivityServiceImpl extends FisheryActivityServiceGrpc.FisheryActivityServiceImplBase{

  private final FisheryActivityService fisheryActivityService;

  public FisheryActivityServiceImpl(FisheryActivityService fisheryActivityService){
    this.fisheryActivityService = fisheryActivityService;
  }

  @Override
  @RolesAllowed({"key2"})
  public void getFisheryActivity(GetFisheryActivityRequest req, StreamObserver<ResponseFisheryActivity> respObs){

    Integer companyId = (Integer) SecurityConstants.COMPANY_ID_CTX_KEY.get().intValue();

    FisheryActivity fisheryActivity = fisheryActivityService.getByIdAndCompanyId(req.getId(), companyId);

    ResponseFisheryActivity response = ResponseFisheryActivity.newBuilder()
            .setId(fisheryActivity.getId())
            .setSetupDateTime(toTimestamp(fisheryActivity.getSetupDateTime()))
            .setToolTypeCode(fisheryActivity.getToolTypeCode())
            .setToolTypeName(fisheryActivity.getToolTypeName())
            .setToolId(fisheryActivity.getToolId())
            .setLastChangedDateTime(toTimestamp(fisheryActivity.getLastChangedDateTime()))
            .setStartingPointLat(fisheryActivity.getStartingPointLat())
            .setStartingPointLon(fisheryActivity.getStartingPointLon())
            .setLength(fisheryActivity.getLength())
            .setGeometry(fisheryActivity.getGeometry())
            .build();

    if (fisheryActivity.getRemovedDateTime() != null) {
      response = response.toBuilder()
              .setRemovedDateTime(toTimestamp(fisheryActivity.getRemovedDateTime()))
              .build();
    }

    respObs.onNext(response);
    respObs.onCompleted();

  }


  @Override
  @RolesAllowed({"key2"})
  public void listFisheryActivities(ListFisheryActivitiesRequest req,
                                 StreamObserver<ListFisheryActivitiesResponse> respObs) {

    Integer companyId = (Integer) SecurityConstants.COMPANY_ID_CTX_KEY.get().intValue();

    List<FisheryActivity> fisheryActivity = fisheryActivityService.getAllFisheryActivitiesWithCompanyId(companyId);

    List<ResponseFisheryActivity> protos = fisheryActivity.stream()
            .map(entity -> {
              var b = ResponseFisheryActivity.newBuilder()
                      .setId(entity.getId())
                      .setSetupDateTime(toTimestamp(entity.getSetupDateTime()))
                      .setToolTypeCode(entity.getToolTypeCode())
                      .setToolTypeName(entity.getToolTypeName())
                      .setToolId(entity.getToolId())
                      .setLastChangedDateTime(toTimestamp(entity.getLastChangedDateTime()))
                      .setStartingPointLat(entity.getStartingPointLat())
                      .setStartingPointLon(entity.getStartingPointLon())
                      .setLength(entity.getLength())
                      .setGeometry(entity.getGeometry());
              if (entity.getRemovedDateTime() != null) {
                b.setRemovedDateTime(toTimestamp(entity.getRemovedDateTime()));
              }
              return b.build();
            })
            .toList();

    // Build and send the wrapper response
    ListFisheryActivitiesResponse reply = ListFisheryActivitiesResponse.newBuilder()
            .addAllActivities(protos)
            .build();

    respObs.onNext(reply);
    respObs.onCompleted();
  }

  private static Timestamp toTimestamp(LocalDateTime ldt) {
    return Timestamp.newBuilder()
            .setSeconds(ldt.toEpochSecond(ZoneOffset.UTC))
            .setNanos(ldt.getNano())
            .build();
  }

}
