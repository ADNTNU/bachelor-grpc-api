package no.ntnu.gr10.bachelor_grpc_api.exception;

import jakarta.persistence.EntityNotFoundException;

/**
 * Custom exception class for handling Fishing Facilities not found.
 * This class extends EntityNotFoundException and is used to indicate
 * that a fishery activity was not found
 *
 * @author Daniel Neset
 * @version 01.05.2025
 */
public class FishingFacilityNotFoundException extends EntityNotFoundException {
  public FishingFacilityNotFoundException(String message) {
    super(message);
  }
}
