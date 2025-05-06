package no.ntnu.gr10.bachelor_grpc_api.fishingFacility;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import no.ntnu.gr10.bachelor_grpc_api.exception.FishingFacilityNotFoundException;
import no.ntnu.gr10.bachelor_grpc_api.security.Role;
import no.ntnu.gr10.bachelor_grpc_api.security.RolesAllowed;
import no.ntnu.gr10.bachelor_grpc_api.security.SecurityConstants;

import java.time.ZoneOffset;
import java.util.List;

@GrpcService
public class FishingFacilityServiceImpl extends FishingFacilityServiceGrpc.FishingFacilityServiceImplBase{

  private final FishingFacilityService fishingFacilityService;

  public FishingFacilityServiceImpl(FishingFacilityService fishingFacilityService){
    this.fishingFacilityService = fishingFacilityService;
  }

  @Override
  @RolesAllowed({Role.FISHING_FACILITY})
  public void getFishingFacility(GetFishingFacilityRequest req,
                                 StreamObserver<ResponseFishingFacility> respObs) {

    Long companyId = SecurityConstants.COMPANY_ID_CTX_KEY.get();

    try {
      FishingFacility entity = fishingFacilityService.getByIdAndCompanyId(req.getId(), companyId.intValue());

      ResponseFishingFacility responseFishingFacility = FishingFacilityMapper
              .toProtoBuilder(entity)
              .build();
      respObs.onNext(responseFishingFacility);
      respObs.onCompleted();

    } catch (FishingFacilityNotFoundException e) {
      respObs.onError(
              Status.NOT_FOUND
                      .withDescription(e.getMessage())
                      .withCause(e)
                      .asRuntimeException()
      );
    } catch (Exception e) {
      respObs.onError(
              Status.INTERNAL
                      .withDescription("Unexpected error: " + e.getMessage())
                      .withCause(e)
                      .asRuntimeException()
      );
    }
  }

  @Override
  @RolesAllowed({Role.FISHING_FACILITY})
  public void listFishingFacilities(ListFishingFacilitiesRequest req,
                                    StreamObserver<ListFishingFacilitiesResponse> respObs) {

    Integer companyId = SecurityConstants.COMPANY_ID_CTX_KEY.get().intValue();

    try {
      List<FishingFacility> entities = fishingFacilityService.getAllFishingFacilitiesWithCompanyId(companyId);

      List<ResponseFishingFacility> protos = entities.stream()
              .map(FishingFacilityMapper::toProtoBuilder)
              .map(ResponseFishingFacility.Builder::build)
              .toList();

      ListFishingFacilitiesResponse reply = ListFishingFacilitiesResponse.newBuilder()
              .addAllFacilities(protos)
              .build();

      respObs.onNext(reply);
      respObs.onCompleted();

    } catch (Exception e) {
      respObs.onError(
              Status.INTERNAL
                      .withDescription("Failed to list facilities: " + e.getMessage())
                      .withCause(e)
                      .asRuntimeException()
      );
    }
  }


  private com.google.protobuf.Timestamp toTimestamp(java.time.LocalDateTime dt) {
    return com.google.protobuf.Timestamp.newBuilder()
            .setSeconds(dt.toEpochSecond(ZoneOffset.UTC))
            .setNanos(dt.getNano())
            .build();
  }

}
