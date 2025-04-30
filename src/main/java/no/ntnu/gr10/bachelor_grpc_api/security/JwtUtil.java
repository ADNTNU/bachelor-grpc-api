package no.ntnu.gr10.bachelor_grpc_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Utility component for JWT operations such as parsing, validating, and extracting application-specific claims.
 * <p>
 * This class centralizes all JWT handling logic to avoid duplication and ensure consistent behavior.
 * </p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
@Component
public class JwtUtil {

  private final JwtParser jwtParser;


  /**
   * Constructs a JwtUtil with the provided secret key for HS256 signature verification.
   *
   * @param jwtSecret the secret key used to sign and verify JWTs
   */
  public JwtUtil(@Value("${jwt.secret_key}") String jwtSecret) {
    this.jwtParser = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
            .build();
  }


  /**
   * Validates the given JWT token's signature and expiration, then returns its claims.
   *
   * @param token the JWT string without "Bearer " prefix
   * @return the parsed {@link Claims} if valid
   * @throws JwtException if the token is invalid, expired, or cannot be parsed
   */
  public Claims validateAndGetClaims(String token) throws JwtException {
    return jwtParser.parseClaimsJws(token).getBody();
  }


  /**
   * Extracts the company identifier from the JWT claims.
   * <p>
   * It first attempts to read the "company" claim, then falls back to "companyId".
   * </p>
   *
   * @param claims the JWT claims object
   * @return the company identifier as a long
   * @throws JwtException if neither "company" nor "companyId" is present
   */
  public long getCompanyId(Claims claims) {
    Number companyNum = claims.get("company", Number.class);
    if (companyNum == null) {
      companyNum = claims.get("companyId", Number.class);
    }
    if (companyNum == null) {
      throw new JwtException("Missing company/companyId claim");
    }
    return companyNum.longValue();
  }


  /**
   * Extracts authority strings from the JWT claims by checking multiple possible claim keys.
   * <p>
   * The order of fallback is:
   * <ol>
   *   <li>"authorities"</li>
   *   <li>"scopes"</li>
   *   <li>"scope"</li>
   * </ol>
   * Each entry is expected to be a map containing an "authority" field.
   * </p>
   *
   * @param claims the JWT claims object
   * @return a list of authority strings; empty if no relevant claim is found
   */
  @SuppressWarnings("unchecked")
  public List<String> getAuthorities(Claims claims) {
    List<Map<String,String>> authObjs = claims.get("authorities", List.class);
    if (authObjs == null) {
      authObjs = claims.get("scopes", List.class);
    }
    if (authObjs == null) {
      authObjs = claims.get("scope", List.class);
    }
    if (authObjs == null) {
      return Collections.emptyList();
    }
    return authObjs.stream()
            .map(m -> m.get("authority"))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
  }

}
