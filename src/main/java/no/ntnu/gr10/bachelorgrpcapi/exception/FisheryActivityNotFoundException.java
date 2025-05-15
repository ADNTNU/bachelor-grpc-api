package no.ntnu.gr10.bachelorgrpcapi.exception;

import jakarta.persistence.EntityNotFoundException;

/**
 * Custom exception class for handling Fishery Activities not found.
 * This class extends EntityNotFoundException and is used to indicate
 * that a fishery activity was not found
 *
 * @author Daniel Neset
 * @version 01.05.2025
 */
public class FisheryActivityNotFoundException extends EntityNotFoundException {
  /**
   * Default constructor for FisheryActivityNotFoundException.
   *
   * @param message the error message to be associated with this exception
   */
  public FisheryActivityNotFoundException(String message) {
    super(message);
  }
}