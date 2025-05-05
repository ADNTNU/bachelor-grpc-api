package no.ntnu.gr10.bachelor_grpc_api.exception;

import jakarta.persistence.EntityNotFoundException;

/**
 * Custom exception class for handling invalid role errors.
 * This class extends EntityNotFoundException and is used to indicate
 * that a fishery activity was not found
 *
 * @author Daniel Neset
 * @version 01.05.2025
 */
public class FisheryActivityNotFoundException extends EntityNotFoundException {
  public FisheryActivityNotFoundException(String message) {
    super(message);
  }
}