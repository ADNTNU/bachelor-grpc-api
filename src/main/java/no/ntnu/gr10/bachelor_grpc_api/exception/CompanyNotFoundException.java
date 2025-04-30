package no.ntnu.gr10.bachelor_grpc_api.exception;

import jakarta.persistence.EntityNotFoundException;

@SuppressWarnings("squid:S110")
public class CompanyNotFoundException extends EntityNotFoundException {
  public CompanyNotFoundException(String message) {
    super(message);
  }
}