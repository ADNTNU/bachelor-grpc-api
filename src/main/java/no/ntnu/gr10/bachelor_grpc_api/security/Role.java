package no.ntnu.gr10.bachelor_grpc_api.security;

/**
 * Enumeration of all application-specific roles/authorities.
 * <p>
 * Define new roles here and reference via {@link RolesAllowed} to keep role names consistent.
 * </p>
 */
public enum Role {

  FISHERY_ACTIVITY("fishery-activity"),
  FISHING_FACILITY("fishing-facility");

  private final String authority;

  Role(String authority) {
    this.authority = authority;
  }

  /**
   * Returns the authority string as it appears in the JWT claim.
   *
   * @return the authority key in the token
   */
  public String getAuthority() {
    return authority;
  }

}
