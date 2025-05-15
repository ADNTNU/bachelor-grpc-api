package no.ntnu.gr10.bachelorgrpcapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Utility component for JWT operations such as parsing, validating,
 * and extracting application-specific claims.
 *
 * <p>This class centralizes all JWT handling logic to avoid duplication and
 * ensure consistent behavior.
 * </p>
 *
 * @author Daniel Neset
 * @version 30.04.2025
 */
@Component
public class JwtUtil {

  @Value("${jwt.secret_key}")
  private String secretKey;


  /**
   * Validates the given JWT token's signature and expiration, then returns its claims.
   *
   * @param token the JWT string without "Bearer " prefix
   * @return the parsed {@link Claims} if valid
   * @throws JwtException if the token is invalid, expired, or cannot be parsed
   */
  public Claims validateAndGetClaims(String token) throws JwtException {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }


  /**
   * Extracts the company identifier from the JWT claims.
   *
   * <p>It first attempts to read the "company" claim, then falls back to "companyId".
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
   *
   * <p>The order of fallback is:
   * <ol>
   *   <li>"authorities"</li>
   *   <li>"scopes"</li>
   *   <li>"scope"</li>
   *  </ol>
   *
   * @param claims the JWT claims object
   * @return a list of authority strings; empty if no relevant claim is found
   */
  @SuppressWarnings("unchecked")
  public List<String> getAuthorities(Claims claims) {
    Object raw = claims.get("scopes", List.class);
    if (raw instanceof List<?>) {
      List<?> list = (List<?>) raw;
      return list.stream()
              .map(item -> {
                if (item instanceof String) {
                  return (String) item;
                }
                return null;
              })
              .filter(Objects::nonNull)
              .toList();
    }

    raw = claims.get("scope", List.class);
    if (raw instanceof List<?> list) {
      return list.stream()
              .map(Object::toString)
              .toList();
    }

    return Collections.emptyList();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
  }

}
