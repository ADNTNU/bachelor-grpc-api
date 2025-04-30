package no.ntnu.gr10.bachelor_grpc_api.security;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RolesAllowed {
  String[] value();
}