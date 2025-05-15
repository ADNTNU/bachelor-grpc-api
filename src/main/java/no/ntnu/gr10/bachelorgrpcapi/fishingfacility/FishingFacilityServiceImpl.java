package no.ntnu.gr10.bachelorgrpcapi.fishingfacility;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import java.time.ZoneOffset;
import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;
import no.ntnu.gr10.bachelorgrpcapi.exception.FishingFacilityNotFoundException;
import no.ntnu.gr10.bachelorgrpcapi.security.SecurityConstants;


/**
 * gRPC service implementation for FishingFacility operations.
 *
 * <p>Provides methods to fetch a single FishingFacility by ID or list all facilities
 * belonging to the authenticated user's company.
 * </p>
 *
 * @author Daniel Neset
 * @version 06.05.2025
 */
@GrpcService
public class FishingFacilityServiceImpl
        extends FishingFacilityServiceGrpc.FishingFacilityServiceImplBase {

  private final FishingFacilityService fishingFacilityService;


  /**
   * Constructs a new FishingFacilityService with the given service.
   *
   * @param fishingFacilityService the business service for FishingFacility operations
   */
  public FishingFacilityServiceImpl(FishingFacilityService fishingFacilityService) {
    this.fishingFacilityService = fishingFacilityService;
  }


  /**
   * Retrieves a single FishsingFacility by its ID.
   *
   * @param req     The request containing the activity ID
   * @param respObs The gRPC stream observer to send the response or error
   */
  @Override
  public void getFishingFacility(GetFishingFacilityRequest req,
                                 StreamObserver<ResponseFishingFacility> respObs) {

    Long companyId = SecurityConstants.COMPANY_ID_CTX_KEY.get();

    try {
      FishingFacility entity = fishingFacilityService
              .getByIdAndCompanyId(req.getId(), companyId.intValue());

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


  /**
   * Lists all FishingFacility entities for the authenticated user's company.
   *
   * @param req     the (possibly empty) request for listing activities
   * @param respObs the gRPC stream observer to send the response or error
   */
  @Override
  public void listFishingFacilities(ListFishingFacilitiesRequest req,
                                    StreamObserver<ListFishingFacilitiesResponse> respObs) {

    Integer companyId = SecurityConstants.COMPANY_ID_CTX_KEY.get().intValue();

    try {
      List<FishingFacility> entities = fishingFacilityService
              .getAllFishingFacilitiesWithCompanyId(companyId);

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
