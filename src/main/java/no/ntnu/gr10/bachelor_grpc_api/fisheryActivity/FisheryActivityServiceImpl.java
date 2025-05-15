package no.ntnu.gr10.bachelor_grpc_api.fisheryActivity;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import no.ntnu.gr10.bachelor_grpc_api.exception.FisheryActivityNotFoundException;
import no.ntnu.gr10.bachelor_grpc_api.security.SecurityConstants;
import com.google.protobuf.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;


/**
 * gRPC service implementation for FisheryActivity operations.
 * <p>
 * Provides methods to fetch a single FisheryActivity by ID or list all activities
 * belonging to the authenticated user's company.
 * </p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
@GrpcService
public class FisheryActivityServiceImpl extends FisheryActivityServiceGrpc.FisheryActivityServiceImplBase{

  private final FisheryActivityService fisheryActivityService;


  /**
   * Constructs a new FisheryActivityServiceImpl with the given service.
   *
   * @param fisheryActivityService the business service for FisheryActivity operations
   */
  public FisheryActivityServiceImpl(FisheryActivityService fisheryActivityService){
    this.fisheryActivityService = fisheryActivityService;
  }


  /**
   * Retrieves a single FisheryActivity by its ID.
   * <p>
   * Requires the caller to have the FISHERY_ACTIVITY role. Reads the company ID from the
   * security context and delegates to the business service. Responds with NOT_FOUND if no
   * activity is found.
   * </p>
   *
   * @param req      the request containing the activity ID
   * @param respObs  the gRPC stream observer to send the response or error
   */
  @Override
  public void getFisheryActivity(GetFisheryActivityRequest req, StreamObserver<ResponseFisheryActivity> respObs){

    Integer companyId = SecurityConstants.COMPANY_ID_CTX_KEY.get().intValue();

    try{
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
    }catch (FisheryActivityNotFoundException e){
      respObs.onError(
              Status.NOT_FOUND
                      .withDescription(e.getMessage())
                      .withCause(e)
                      .asRuntimeException()
      );
    }catch (Exception e){
      respObs.onError(
              Status.INTERNAL
                      .withDescription(e.getMessage())
                      .withCause(e)
                      .asRuntimeException()
      );
    }

  }


  /**
   * Lists all FisheryActivity entities for the authenticated user's company.
   * <p>
   * Returns an empty list if no activities exist. Requires the FISHERY_ACTIVITY role.
   * </p>
   *
   * @param req      the (possibly empty) request for listing activities
   * @param respObs  the gRPC stream observer to send the response or error
   */
  @Override
  public void listFisheryActivities(ListFisheryActivitiesRequest req,
                                 StreamObserver<ListFisheryActivitiesResponse> respObs) {

    Integer companyId = SecurityConstants.COMPANY_ID_CTX_KEY.get().intValue();

    try {
      List<FisheryActivity> fisheryActivity = fisheryActivityService.getAllFisheryActivitiesWithCompanyId(companyId);

      List<ResponseFisheryActivity> proto = fisheryActivity.stream()
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

      ListFisheryActivitiesResponse reply = ListFisheryActivitiesResponse.newBuilder()
              .addAllActivities(proto)
              .build();

      respObs.onNext(reply);
      respObs.onCompleted();
    }catch (Exception e){
      respObs.onError(
              Status.INTERNAL
                      .withDescription(e.getMessage())
                      .withCause(e)
                      .asRuntimeException()
      );
    }
  }


  /**
   * Converts a Java LocalDateTime to a Protobuf Timestamp in UTC.
   *
   * @param ldt the LocalDateTime to convert
   * @return the corresponding protobuf Timestamp
   */
  private static Timestamp toTimestamp(LocalDateTime ldt) {
    return Timestamp.newBuilder()
            .setSeconds(ldt.toEpochSecond(ZoneOffset.UTC))
            .setNanos(ldt.getNano())
            .build();
  }

}
