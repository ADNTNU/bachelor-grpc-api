package no.ntnu.gr10.bachelorgrpcapi.fishingfacility;

import com.google.protobuf.Timestamp;
import java.time.ZoneOffset;

/**
 * Utility to map between FishingFacility JPA entities and gRPC proto messages.
 *
 * @author Daniel Neset
 * @version 06.05.2025
 */
public class FishingFacilityMapper {

  /**
   * Convert a FishingFacility entity into a ResponseFishingFacility.Builder,
   * setting only non-null fields on the proto.
   *
   * @param entity the JPA entity to map
   * @return a Builder pre-populated with all fields
   */
  public static ResponseFishingFacility.Builder toProtoBuilder(FishingFacility entity) {
    ResponseFishingFacility.Builder b = ResponseFishingFacility.newBuilder()
            .setId(entity.getId())
            .setType(entity.getType())
            .setBbox(entity.getBbox())
            .setGeometry(entity.getGeometry())
            .setVersion(entity.getVersion())
            .setSetupDateTime(toTimestamp(entity.getSetupDateTime()))
            .setToolId(entity.getToolId());

    if (entity.getVesselName() != null) {
      b.setVesselName(entity.getVesselName());
    }
    if (entity.getVesselPhone() != null) {
      b.setVesselPhone(entity.getVesselPhone());
    }
    if (entity.getToolTypeCode() != null) {
      b.setToolTypeCode(entity.getToolTypeCode());
    }
    if (entity.getIrcs() != null) {
      b.setIrcs(entity.getIrcs());
    }
    if (entity.getMmsi() != null) {
      b.setMmsi(entity.getMmsi());
    }
    if (entity.getImo() != null) {
      b.setImo(entity.getImo());
    }
    if (entity.getVesselEmail() != null) {
      b.setVesselEmail(entity.getVesselEmail());
    }
    if (entity.getToolTypeName() != null) {
      b.setToolTypeName(entity.getToolTypeName());
    }
    if (entity.getToolColor() != null) {
      b.setToolColor(entity.getToolColor());
    }
    if (entity.getSource() != null) {
      b.setSource(entity.getSource());
    }
    if (entity.getComment() != null) {
      b.setComment(entity.getComment());
    }
    if (entity.getLastChangedBySource() != null) {
      b.setLastChangedBySource(entity.getLastChangedBySource());
    }
    if (entity.getRegNum() != null) {
      b.setRegNum(entity.getRegNum());
    }
    if (entity.getSbrRegNum() != null) {
      b.setSbrRegNum(entity.getSbrRegNum());
    }

    if (entity.getRemovedDateTime() != null) {
      b.setRemovedDateTime(toTimestamp(entity.getRemovedDateTime()));
    }
    if (entity.getLastChangedDateTime() != null) {
      b.setLastChangedDateTime(toTimestamp(entity.getLastChangedDateTime()));
    }
    if (entity.getSetupProcessedTime() != null) {
      b.setSetupProcessedTime(toTimestamp(entity.getSetupProcessedTime()));
    }
    if (entity.getRemovedProcessedTime() != null) {
      b.setRemovedProcessedTime(toTimestamp(entity.getRemovedProcessedTime()));
    }

    b.setToolCount(entity.getToolCount() != null ? entity.getToolCount() : 0);

    return b;
  }

  /**
   * Convert a LocalDateTime to a Protobuf Timestamp in UTC.
   */
  private static Timestamp toTimestamp(java.time.LocalDateTime dt) {
    return Timestamp.newBuilder()
            .setSeconds(dt.toEpochSecond(ZoneOffset.UTC))
            .setNanos(dt.getNano())
            .build();
  }
}

