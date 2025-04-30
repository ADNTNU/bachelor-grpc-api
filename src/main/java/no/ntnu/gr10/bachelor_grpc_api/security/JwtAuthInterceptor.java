package no.ntnu.gr10.bachelor_grpc_api.security;

import io.grpc.BindableService;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * gRPC server interceptor that handles JWT-based authentication and authorization.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Extract and validate the JWT from the "authorization" Metadata header.</li>
 *   <li>Parse out application-specific claims (companyId, authorities).</li>
 *   <li>Populate the gRPC {@link Context} with these claims for downstream handlers.</li>
 *   <li>Enforce method-level access control via {@link RolesAllowed} annotations.</li>
 * </ul>
 * </p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
@GrpcGlobalServerInterceptor
@Component
public class JwtAuthInterceptor implements ServerInterceptor {

  private final JwtUtil jwtUtil;
  private final Map<String, BindableService> serviceBeans;


  /**
   * Constructs the interceptor with a {@link JwtUtil} for token handling and the Spring context
   * for locating gRPC service beans.
   *
   * @param jwtUtil the utility for JWT parsing and validation
   * @param applicationContext the Spring application context to retrieve gRPC services
   */
  public JwtAuthInterceptor(JwtUtil jwtUtil, ApplicationContext applicationContext) {
    this.jwtUtil = jwtUtil;
    this.serviceBeans = applicationContext
            .getBeansOfType(BindableService.class)
            .values()
            .stream()
            .collect(Collectors.toMap(
                    bs -> bs.bindService().getServiceDescriptor().getName(),
                    bs -> bs
            ));
  }


  /**
   * Intercepts each gRPC call to enforce JWT authentication and optional authorization.
   *
   * @param call the server call being invoked
   * @param headers the incoming metadata headers
   * @param next the handler to invoke if authentication/authorization succeeds
   * @param <ReqT> the request type
   * @param <ResT> the response type
   * @return a listener for the request stream
   */
  @Override
  public <ReqT, ResT> ServerCall.Listener<ReqT> interceptCall(
          ServerCall<ReqT, ResT> call,
          Metadata headers,
          ServerCallHandler<ReqT, ResT> next
  ) {

    String raw = headers.get(SecurityConstants.AUTH_HEADER);
    if (raw == null || !raw.startsWith("Bearer ")) {
      call.close(Status.UNAUTHENTICATED.withDescription("Missing token"), new Metadata());
      return new ServerCall.Listener<>() {};
    }
    String token = raw.substring("Bearer ".length());

    Claims claims;
    try {
      claims = jwtUtil.validateAndGetClaims(token);
    } catch (JwtException e) {
      call.close(Status.UNAUTHENTICATED.withDescription("Invalid token"), new Metadata());
      return new ServerCall.Listener<>() {};
    }

    long companyId;
    List<String> authorities;
    try {
      companyId = jwtUtil.getCompanyId(claims);
      authorities = jwtUtil.getAuthorities(claims);
    } catch (JwtException e) {
      call.close(Status.UNAUTHENTICATED.withDescription(e.getMessage()), new Metadata());
      return new ServerCall.Listener<>() {};
    }

    Context ctx = Context.current()
            .withValue(SecurityConstants.COMPANY_ID_CTX_KEY, companyId)
            .withValue(SecurityConstants.AUTHORITIES_CTX_KEY, authorities);

    String serviceName = call.getMethodDescriptor().getServiceName();
    String rpcMethod   = call.getMethodDescriptor().getBareMethodName();
    String javaMethod  = Character.toLowerCase(rpcMethod.charAt(0)) + rpcMethod.substring(1);

    BindableService svcBean = serviceBeans.get(serviceName);
    if (svcBean != null) {
      Method target = Arrays.stream(svcBean.getClass().getMethods())
              .filter(m -> m.getName().equals(javaMethod))
              .findFirst()
              .orElse(null);

      if (target != null && target.isAnnotationPresent(RolesAllowed.class)) {
        Role[] roles = target.getAnnotation(RolesAllowed.class).value();
        Set<String> required = Arrays.stream(roles)
                .map(Role::getAuthority)
                .collect(Collectors.toSet());
        if (!new HashSet<>(authorities).containsAll(required)) {
          call.close(Status.PERMISSION_DENIED.withDescription("Not authorized"), new Metadata());
          return new ServerCall.Listener<>() {};
        }
      }
    }

    return Contexts.interceptCall(ctx, call, headers, next);
  }
}
