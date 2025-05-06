package no.ntnu.gr10.bachelor_grpc_api.security;

import io.grpc.Context;
import io.grpc.Metadata;


/**
 * Defines gRPC security-related constants for metadata keys and context keys.
 * <p>
 * These constants are used for extracting authorization headers and
 * propagating company ID and user authorities within gRPC contexts.
 * </p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
public class SecurityConstants {
  public static final Metadata.Key<String> AUTH_HEADER =
          Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

  public static final Context.Key<Long> COMPANY_ID_CTX_KEY =
          Context.key("companyId");

  public static final Context.Key<java.util.List<String>> AUTHORITIES_CTX_KEY =
          Context.key("authorities");
}
